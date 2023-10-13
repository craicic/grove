import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Account} from '../../../../model/account.model';
import {AccountService} from './account.service';

@Injectable({providedIn: 'root'})
export class AccountResolver implements Resolve<Account> {

  constructor(private service: AccountService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Account> | Promise<Account> | Account {
    const account: Account = this.service.account;
    if (!account) {
      const param = route.params['id'];
      return this.service.fetchById(+param);
    }
    return account;
  }

}
