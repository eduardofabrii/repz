import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, PLATFORM_ID, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '@env/environment';

export type UserRole = 'ADMIN' | 'GERENTE' | 'PERSONAL' | 'ALUNO';

export interface UserCreateRequest {
  name: string;
  email: string;
  password: string;
  role: UserRole;
  academiaId: number;
  planoId?: number;
}

export interface UserPutRequest {
  name: string;
  email: string;
  role?: UserRole;
  active?: boolean;
  academiaId?: number;
}

export interface UserSelfUpdateRequest {
  name: string;
  email: string;
  senha?: string;
}

export interface UserGetResponse {
  id: number;
  name: string;
  email: string;
  lastLogin?: string;
  role: UserRole;
  active: boolean;
  fotoUrl?: string;
}

const USER_NAME_KEY = 'USER_NAME';
const USER_FOTO_KEY = 'USER_FOTO';

@Injectable({ providedIn: 'root' })
export class UserService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiUrl}/api/users`;
  private readonly platformId = inject(PLATFORM_ID);

  private readonly _nomeUsuario = signal<string>(this.lerStorage(USER_NAME_KEY) ?? '');
  readonly nomeUsuario = computed(() => this._nomeUsuario());
  private readonly _fotoUrl = signal<string | null>(this.lerStorage(USER_FOTO_KEY));
  readonly fotoUrl = computed(() => this._fotoUrl());
  private nomeCarregado = false;

  criar(req: UserCreateRequest): Observable<void> {
    return this.http.post<void>(this.base, req);
  }

  listar(): Observable<UserGetResponse[]> {
    return this.http.get<UserGetResponse[]>(this.base);
  }

  atualizar(id: number, req: UserPutRequest): Observable<void> {
    return this.http.put<void>(`${this.base}/${id}`, req);
  }

  ativar(id: number): Observable<void> {
    return this.http.patch<void>(`${this.base}/${id}/ativar`, {});
  }

  desativar(id: number): Observable<void> {
    return this.http.patch<void>(`${this.base}/${id}/desativar`, {});
  }

  meuPerfil(): Observable<UserGetResponse> {
    return this.http.get<UserGetResponse>(`${this.base}/me`).pipe(
      tap((u) => {
        this.salvarNome(u.name ?? '');
        this.salvarFoto(u.fotoUrl ?? null);
        this.nomeCarregado = true;
      }),
    );
  }

  atualizarMeuPerfil(req: UserSelfUpdateRequest): Observable<UserGetResponse> {
    return this.http.put<UserGetResponse>(`${this.base}/me`, req).pipe(
      tap((u) => this.salvarNome(u?.name ?? req.name)),
    );
  }

  uploadFoto(file: File): Observable<UserGetResponse> {
    const form = new FormData();
    form.append('foto', file);
    return this.http.post<UserGetResponse>(`${this.base}/me/foto`, form).pipe(
      tap((u) => {
        this.salvarNome(u?.name ?? '');
        this.salvarFoto(u?.fotoUrl ?? null);
      }),
    );
  }

  carregarNomeLogado(): void {
    if (this.nomeCarregado) return;
    this.nomeCarregado = true;
    this.meuPerfil().subscribe({
      error: () => {
        this.nomeCarregado = false;
      },
    });
  }

  setFotoUrl(url: string | null): void {
    this.salvarFoto(url);
  }

  resetar(): void {
    this._nomeUsuario.set('');
    this._fotoUrl.set(null);
    this.nomeCarregado = false;
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(USER_NAME_KEY);
      localStorage.removeItem(USER_FOTO_KEY);
    }
  }

  private salvarNome(nome: string): void {
    this._nomeUsuario.set(nome);
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(USER_NAME_KEY, nome);
    }
  }

  private salvarFoto(url: string | null): void {
    this._fotoUrl.set(url);
    if (isPlatformBrowser(this.platformId)) {
      if (url) localStorage.setItem(USER_FOTO_KEY, url);
      else localStorage.removeItem(USER_FOTO_KEY);
    }
  }

  private lerStorage(key: string): string | null {
    try {
      return isPlatformBrowser(this.platformId) ? localStorage.getItem(key) : null;
    } catch {
      return null;
    }
  }
}
