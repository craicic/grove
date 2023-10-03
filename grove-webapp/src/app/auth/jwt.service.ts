import {Injectable} from '@angular/core';
import {AccessToken} from './access-token';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor() {
  }

  decode(token: string): AccessToken {
    const payload = token.split('.')[1];
    const decoded = atob(payload);
    return JSON.parse(decoded);
  }
}
