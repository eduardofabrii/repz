import { CanActivateFn, Router} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth';


export const academiaGuard: CanActivateFn = () => {
  const router = inject(Router);
  const authService = inject(AuthService);

  const userRole = authService.getUserRole();

  if (userRole === 'ACADEMIA') {
    return true;
  }

  router.navigate(['/auth']);
  return false;

};
