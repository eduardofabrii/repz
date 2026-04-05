import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const adminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  
  const userRole = localStorage.getItem('USER_ROLE'); 

  if (userRole === 'ADMIN') {
    return true; 
  }

  router.navigate(['/auth']);
  return false;
};