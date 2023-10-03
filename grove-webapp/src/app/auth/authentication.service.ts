import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {JwtService} from './jwt.service';
import {AccessToken} from './access-token';
import {interval, Observable, Subscription} from 'rxjs';
import {take, tap} from 'rxjs/operators';
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
  public requestedUrl: string;

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
          this.initTimer();
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

  refreshExpired(): boolean {
    return this.refresh.exp * 1000 < (Date.now() - 1000);
  }

  accessExpired(): boolean {
    return this.token.exp * 1000 < (Date.now() - 1000);
  }

  initTimer(): void {
    this.timer = this.refreshTask();
  }

  refreshToken(): Observable<Jwt> {
    console.log('Refreshing access token !');
    return this.http
      .get<Jwt>(environment.apiUri + '/api/token')
      .pipe(tap((response: Jwt) => {
        this.loadProfile(response);
        console.log('profile up to date');
        this.timer?.unsubscribe();
        this.initTimer();
      }));
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
    console.log('Reset profile');
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
    const refreshInterval: number = Math.floor(this.expiresIn * 0.92);
    return interval(1000).pipe(take(refreshInterval)).subscribe(
      (t) => t
      // (t % 10) === 0 ? console.log('Refreshing access in ' + (refreshInterval - t) + 's') : t
      ,
      (error) => console.log(error),
      () => this.refreshToken().subscribe());
  }

  getRoles(): string[] | null {
    return this.roles ? this.roles.slice() : null;
  }
}

type Jwt = {
  access_token: string;
  refresh_token: string;
};

