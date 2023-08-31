import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authenticated: boolean;

  constructor(private http: HttpClient) {
    this.authenticated = false;
  }

  authenticate(credentials: { username: string; password: string; }, callback: () => any): any {
    const params = new HttpParams({fromObject: credentials});

    this.http.post<any>(environment.apiUri + '/api/login', params)
      .subscribe((response) => {
        if (response.access_token && response.refresh_token) {
          this.authenticated = true;
          localStorage.setItem('access_token', response.access_token);
          localStorage.setItem('refresh_token', response.access_token);
        } else {
          this.authenticated = false;
        }
        console.log(response);
        return callback && callback();
      }, (error => console.log(error)));
  }

  refreshToken(): void {
    this.http.get<any>(environment.apiUri + '/api/token')
      .subscribe((response => console.log(response)));
  }

  invalidate(callback: () => any): any {
    this.authenticated = false;
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');

    return callback && callback();
  }
}
