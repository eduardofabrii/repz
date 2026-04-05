import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  getToken(): string | null {
    return localStorage.getItem('JWT_TOKEN');
  }

  getUserRole(): string | null {
    return localStorage.getItem('USER_ROLE');
  }

}