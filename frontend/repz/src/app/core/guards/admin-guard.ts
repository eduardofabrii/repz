import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth';

export const adminGuard: CanActivateFn = () => {
  const router = inject(Router);
  const authService = inject(AuthService);

  const userRole = authService.getUserRole();

  if (userRole === 'ADMIN') {
    return true;
  }

  router.navigate(['/auth']);
  return false;
};
