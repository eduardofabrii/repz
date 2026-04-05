import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-auth',
  standalone: true, 
  imports: [FormsModule], 
  templateUrl: './auth.html',
  styleUrls: ['./auth.scss'],
})
export class Auth {
  credenciais = {
    email: '',
    password: ''
  };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // realizarLogin() {
  //   this.authService.login(this.credenciais).subscribe({
  //     next: (response: any) => {
  //       this.authService.saveToken(response.token);
        
  //       localStorage.setItem('USER_ROLE', response.role); 

  //       //this.redirecionarPorPerfil(response.role);
  //     },
  //     error: (err) => {
  //       console.error('Erro no login', err);
  //       alert('Credenciais inválidas ou erro no servidor!');
  //     }
  //   });
  // }

  // redirecionarPorPerfil(role: string) {
  //   switch(role) {
  //     case 'ADMIN':
  //       this.router.navigate(['/admin']);
  //       break;
  //     case 'ACADEMIA':
  //       this.router.navigate(['/academia']);
  //       break;
  //     case 'PERSONAL':
  //       this.router.navigate(['/personal']);
  //       break;
  //     case 'ALUNO':
  //       this.router.navigate(['/aluno']);
  //       break;
  //     default:
  //       // Se vier uma role desconhecida, fica no login
  //       this.router.navigate(['/auth']); 
  //   }
  // }
}