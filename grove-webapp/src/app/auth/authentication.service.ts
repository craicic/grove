import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    authenticated: boolean;

    constructor(private http: HttpClient) {
        this.authenticated = false;
    }

    authenticate(credentials: { username: string; password: string; } | undefined, callback: () => any): any {
        this.http.post(environment.apiUri + '/api/login', {
            username: credentials.username,
            password: credentials.password
        }).subscribe((response: { access_token: string, refresh_token: string; }) => {
            if (response.access_token && response.refresh_token) {
                this.authenticated = true;
            } else {
                this.authenticated = false;
            }
            return callback && callback();
        }, (error => console.log(error)));
    }
}
