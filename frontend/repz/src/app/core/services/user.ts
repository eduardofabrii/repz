import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';

export type UserRole = 'ADMIN' | 'GERENTE' | 'PERSONAL' | 'ALUNO';

export interface UserCreateRequest {
  name: string;
  email: string;
  password: string;
  role: UserRole;
  academiaId: number;
  /** Obrigatório quando role = ALUNO; ignorado para PERSONAL/GERENTE. */
  planoId?: number;
}

export interface UserPutRequest {
  name: string;
  email: string;
  role?: UserRole;
  active?: boolean;
}

export interface UserGetResponse {
  id: number;
  name: string;
  email: string;
  lastLogin?: string;
  role: UserRole;
  active: boolean;
}

/**
 * Cadastro de PERSONAL e ALUNO acontece pelo endpoint genérico /api/users
 * (a role é definida no payload). Por isso este service é usado pelas
 * telas de criação dos features de Personal e Aluno.
 */
@Injectable({ providedIn: 'root' })
export class UserService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiUrl}/api/users`;

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
}
