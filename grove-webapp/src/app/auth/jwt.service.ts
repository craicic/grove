import {Injectable} from '@angular/core';
import {AccessToken} from './access-token';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() {
  }

  extractData(token: string): AccessToken {
    const payload = token.split('.')[1];
    const decoded = atob(payload);
    console.log(decoded);
    return JSON.parse(decoded);
  }
}
