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
            .subscribe((response: { access_token: string, refresh_token: string; }) => {
                if (response.access_token && response.refresh_token) {
                    this.authenticated = true;
                } else {
                    this.authenticated = false;
                }
                console.log(response);
                return callback && callback();
            }, (error => console.log(error)));
    }
}
