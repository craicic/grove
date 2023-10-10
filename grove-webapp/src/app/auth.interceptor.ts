import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {AuthenticationService} from './auth/authentication.service';
import {environment} from '../environments/environment';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token: string;
    const accessToken = localStorage.getItem('access_token')?.toString();
    const refreshToken = localStorage.getItem('refresh_token')?.toString();
    console.log(req.url);
    if (this.authService.authenticated && req.url.endsWith('/api/token')) {
      console.log('Sending refresh token to backend!');
      token = refreshToken;
      req = req.clone({headers: req.headers.set('Authorization', 'Bearer ' + token)});

    } else if (this.authService.authenticated && req.url.startsWith(environment.apiUri + '/api')) {
      console.log('Append the access to the request header !');
      token = accessToken;
      req = req.clone({headers: req.headers.set('Authorization', 'Bearer ' + token)});
    }
    return next.handle(req);
  }
}
