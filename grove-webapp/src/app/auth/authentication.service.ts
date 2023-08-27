import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    authenticated: boolean;
    private headers: HttpHeaders;

    constructor(private http: HttpClient) {
        this.authenticated = false;
    }

    authenticate(credentials: { username: string; password: string; }, callback: () => any): any {
        this.headers = new HttpHeaders(credentials ?
            {authorization: ' ' + btoa(credentials.username + ':' + credentials.password)} : {});

        this.http.post(environment.apiUri + '/api/login', {
            username: credentials.username,
            password: credentials.password
        }).subscribe((response: { name: string; }) => {
            if (response.name) {
                this.authenticated = true;
            } else {
                this.authenticated = false;
            }
            return callback && callback();
        });
    }
}
