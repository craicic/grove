import {Component, OnDestroy, OnInit} from '@angular/core';
import {LoanService} from '../loan.service';
import {Observable} from 'rxjs';
import {Account} from '../../../../../model/account.model';
import {Router} from '@angular/router';
import {map, tap} from 'rxjs/operators';

@Component({
  selector: 'app-select-member',
  templateUrl: './select-member.component.html',
  styleUrls: ['./select-member.component.css']
})
export class SelectMemberComponent implements OnInit, OnDestroy {

  accounts$: Observable<Account[]>;

  constructor(private service: LoanService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.accounts$ = this.service.fetchActiveAccounts();
  }

  ngOnDestroy(): void {

  }

  onSelectMember(accountId: number): void {
    // We store the account in service.
    this.accounts$.pipe(
      map(accounts => accounts.find(account => account.id === accountId)),
      tap(x => console.log(x))).subscribe(value => {
      this.service.account = value;
      this.router.navigate(['admin/loans/', accountId, 'select-game']);
    });


  }

  onNavigateToDetail(accountId: number): void {
    this.router.navigate(['admin/members/', accountId]);
  }
}
