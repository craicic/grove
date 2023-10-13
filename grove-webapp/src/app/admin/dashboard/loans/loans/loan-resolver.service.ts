import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Loan} from '../../../../model/loan.model';
import {Observable} from 'rxjs';
import {LoanService} from './loan.service';
import {tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class LoanResolver  {


  constructor(private service: LoanService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Loan> | Promise<Loan> | Loan {

    const id = 'id';
    const loanId = route.params[id];
    if (this.service.loan && this.service.loan.id === +loanId) {
      return this.service.loan;
    }

    return this.service.fetchById(+loanId).pipe(tap(loan => this.service.loan = loan));
  }


}
