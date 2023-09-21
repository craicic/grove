import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {AuthenticationService} from './auth/authentication.service';
import {Environment} from '@angular/cli/lib/config/workspace-schema';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token: string;
    console.log(req.url);
    if (this.authService.authenticated && req.url.endsWith('/api/token')) {
      console.log('Sending refresh token to backend!');
      token = localStorage.getItem('refresh_token').toString();
    } else if (this.authService.authenticated && req.url.startsWith('http://localhost:8080/api')) {
      token = localStorage.getItem('access_token').toString();
    }
    req = req.clone(
      {headers: req.headers.set('Authorization', 'Bearer ' + token)}
    );
    return next.handle(req);
  }
}
