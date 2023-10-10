import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from './auth/authentication.service';

export const permissionGuard = (): any => {
  const authService = inject(AuthenticationService);
  const router = inject(Router);

  console.log('Route for admin role');
  console.table('Role = ' + authService.getRoles());
  if (authService.authenticated && authService.getRoles() && authService.getRoles().includes('ROLE_ADMIN')) {
    return true;
  }
  return router.parseUrl('/login');
};
