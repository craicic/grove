import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from './auth/authentication.service';

@Injectable({providedIn: 'root'})
export class PermissionService {

  constructor(protected readonly router: Router,
              private authService: AuthenticationService) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('Route for admin role');
    console.table(this.authService.getRoles());
    return this.authService.authenticated && this.authService.getRoles().includes('ROLE_ADMIN');
  }
}

export const AuthGuard = (): any => {
  const authService = inject(AuthenticationService);
  const router = inject(Router);

  console.log('Route for admin role');
  console.table('Role = ' + authService.getRoles());
  if (authService.authenticated && authService.getRoles() && authService.getRoles().includes('ROLE_ADMIN')) {
    return true;
  }

  return router.parseUrl('/login');
};
