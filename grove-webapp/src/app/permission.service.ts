import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
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
export const AuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(PermissionService).canActivate(next, state);
};
