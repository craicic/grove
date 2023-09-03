import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {JwtService} from './jwt.service';
import {AccessToken} from './access-token';
import {interval, Subscription} from 'rxjs';
import {take} from 'rxjs/operators';
import {RefreshToken} from './refresh-token';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authenticated: boolean;
  private roles: string[];
  private username: string;
  private expiresIn: number;
  private timer: Subscription;
  private token: AccessToken;
  private refresh: RefreshToken;


  constructor(private http: HttpClient,
              private jwtService: JwtService) {
    this.authenticated = false;
  }

  authenticate(credentials: { username: string; password: string; }, callback: () => any): any {
    const params = new HttpParams({fromObject: credentials});

    this.http.post<Jwt>(environment.apiUri + '/api/login', params)
      .subscribe((response: Jwt) => {
        if (response.access_token && response.refresh_token) {
          this.loadProfile(response);
          this.timer = this.refreshTask();
        } else {
          this.authenticated = false;
        }
        console.log(response);
        return callback && callback();
      }, (error) => console.log(error));
  }

  loadProfile(data: Jwt): void {
    this.token = this.jwtService.decode(data.access_token);
    this.refresh = this.jwtService.decode(data.refresh_token);
    this.expiresIn = this.token.exp - this.token.iat;
    this.username = this.token.sub;
    this.roles = this.token.roles.slice();
    localStorage.setItem('access_token', data.access_token);
    localStorage.setItem('refresh_token', data.refresh_token);
    this.authenticated = true;
  }

  refreshToken(): void {
    console.log('Refreshing access token !');
    this.http.get<Jwt>(environment.apiUri + '/api/token')
      .subscribe((response: Jwt) => {
          this.loadProfile(response);
        },
        (error) => console.log(error),
        () => {
          this.timer.unsubscribe();
          this.timer = this.refreshTask();
        }
      );
  }

  invalidate(callback: () => any): any {
    console.log('Logout initiated');
    this.resetProfile();
    if (this.timer !== null) {
      this.timer.unsubscribe();
    }
    return callback && callback();
  }

  resetProfile(): any {
    this.expiresIn = null;
    this.username = null;
    this.roles = null;
    this.token = null;
    this.refresh = null;
    this.authenticated = false;
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
  }

  refreshTask(): Subscription {
    console.log('refresh tasks starts');
    const refreshInterval: number = Math.floor(this.expiresIn * 0.85);
    return interval(1000).pipe(take(refreshInterval)).subscribe(
      (t) => (t % 10) === 0 ? console.log('Refreshing access in ' + (refreshInterval - t) + 's') : t,
      (error) => console.log(error),
      () => this.refreshToken());
  }

  getRoles(): string[] {
    return this.roles.slice();
  }
}

type Jwt = {
  access_token: string;
  refresh_token: string;
};

