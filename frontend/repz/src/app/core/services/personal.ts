import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';

export interface PersonalResponse {
  id: number;
  userId: number;
  userName: string;
  email: string;
  academiaId: number;
  academiaNome?: string;
  especialidade?: string;
  ativo: boolean;
}

export interface PersonalUpdateRequest {
  especialidade: string;
  ativo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class PersonalService {
  private readonly http = inject(HttpClient);
  private readonly base = `${environment.apiUrl}/api/personais`;

  private headers(academiaId?: number | null): { headers?: HttpHeaders } {
    return academiaId
      ? { headers: new HttpHeaders({ 'X-Academia-Id': String(academiaId) }) }
      : {};
  }

  listar(academiaId?: number | null): Observable<PersonalResponse[]> {
    return this.http.get<PersonalResponse[]>(this.base, this.headers(academiaId));
  }

  buscar(id: number): Observable<PersonalResponse> {
    return this.http.get<PersonalResponse>(`${this.base}/${id}`);
  }

  atualizar(
    id: number,
    req: PersonalUpdateRequest,
    academiaId?: number | null,
  ): Observable<PersonalResponse> {
    return this.http.put<PersonalResponse>(`${this.base}/${id}`, req, this.headers(academiaId));
  }

  ativar(id: number, academiaId?: number | null): Observable<PersonalResponse> {
    return this.http.patch<PersonalResponse>(
      `${this.base}/${id}/ativar`,
      {},
      this.headers(academiaId),
    );
  }

  desativar(id: number, academiaId?: number | null): Observable<PersonalResponse> {
    return this.http.patch<PersonalResponse>(
      `${this.base}/${id}/desativar`,
      {},
      this.headers(academiaId),
    );
  }
}
