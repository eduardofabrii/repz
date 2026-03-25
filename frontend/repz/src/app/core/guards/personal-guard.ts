import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const personalGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  const userRole = localStorage.getItem('USER_ROLE'); 

  if (userRole === 'PERSONAL') {
    return true; 
  }

  router.navigate(['/auth']);
  return false;
};
