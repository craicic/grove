import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {JwtService} from './jwt.service';
import {AccessToken} from './access-token';
import {interval, Subscription} from 'rxjs';
import {take} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authenticated: boolean;
  timer: Subscription;

  constructor(private http: HttpClient,
              private jwtService: JwtService) {
    this.authenticated = false;
  }

  authenticate(credentials: { username: string; password: string; }, callback: () => any): any {
    const params = new HttpParams({fromObject: credentials});

    this.http.post<Jwt>(environment.apiUri + '/api/login', params)
      .subscribe((response: Jwt) => {
        if (response.access_token && response.refresh_token) {
          this.authenticated = true;
          localStorage.setItem('access_token', response.access_token);
          localStorage.setItem('refresh_token', response.refresh_token);
          this.timer = this.refreshTask(response.access_token);
        } else {
          this.authenticated = false;
        }
        console.log(response);
        return callback && callback();
      }, (error) => console.log(error));
  }

  refreshToken(): void {
    let encodedToken: string;
    console.log('Refreshing access token !');
    this.http.get<Jwt>(environment.apiUri + '/api/token')
      .subscribe((response) => {
          console.log(response);
          encodedToken = response.access_token;
        },
        (error) => console.log(error),
      () => {
        this.timer.unsubscribe();
        this.timer = this.refreshTask(encodedToken);
        console.log('post completed');
      }
      );
  }

  invalidate(callback: () => any): any {
    console.log('Logout initiated');
    this.authenticated = false;
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    if (this.timer !== null) {
      this.timer.unsubscribe();
    }
    return callback && callback();
  }

  invalidateOnly(): any {
    this.authenticated = false;
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
  }

  refreshTask(encodedToken: string): Subscription {
    console.log('refresh tasks starts');
    const token: AccessToken = this.jwtService.extractData(encodedToken);
    const expiresIn: number = token.exp - token.iat;
    const refreshInterval: number = Math.floor(expiresIn * 0.85);
    return interval(1000).pipe(take(refreshInterval)).subscribe(
      (t) => (t % 10) === 0 ? console.log('Refreshing access in ' + (refreshInterval - t) + 's') : t,
      (error) => console.log(error),
      () => this.refreshToken());
  }
}

type Jwt = {
  access_token: string;
  refresh_token: string;
};

