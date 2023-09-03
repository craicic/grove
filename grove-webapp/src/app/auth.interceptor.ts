import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {AuthenticationService} from './auth/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(req.url);
    if (this.authService.authenticated && req.url.endsWith('/api/token')) {
      req.clone(
        {headers: req.headers.set('Authorization', 'Bearer ' + localStorage.getItem('refresh_token').toString().trim())}
      );
    } else if (this.authService.authenticated) {
      req = req.clone(
        {headers: req.headers.set('Authorization', 'Bearer ' + localStorage.getItem('access_token').toString().trim())}
      );
    }
    return next.handle(req);
  }
}
