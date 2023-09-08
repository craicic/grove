import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import {Account} from '../../model/account.model';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class AccountService {
  apiUri: string;
  account: Account;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient) {
    this.apiUri = environment.apiUri;
  }

  saveAndStoreAccount(account: Account): Observable<Account> {
    return this.saveAccount(account).pipe(tap(a => this.account = a));
  }

  saveAccount(account: Account): Observable<Account> {
    let contactParam = '';
    if (account.contact) {
      contactParam = '?has-contact=true';
    }
    return this.http.post<Account>(this.apiUri + '/api/admin/accounts' + contactParam, account);
  }

  fetchAll(): Observable<Account[]> {
    return this.http.get<Account[]>(this.apiUri + '/api/admin/accounts');
  }

  fetchById(id: number): Observable<Account> {
    return this.http.get<Account>(this.apiUri + '/api/admin/accounts/' + id);
  }

  fetchActives(): Observable<Account[]> {
    return this.http.get<Account[]>(this.apiUri + '/api/admin/accounts/no-current-loan');
  }
}
