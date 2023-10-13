import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Loan} from '../../../../../model/loan.model';
import {LoanService} from '../loan.service';
import {map, tap} from 'rxjs/operators';
import {Router} from '@angular/router';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.css']
})
export class LoanListComponent implements OnInit {

  loans$: Observable<Loan[]>;

  constructor(private service: LoanService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loans$ = this.service.fetchAll();
  }

  onNavigateToDetail(loanId: number): void {
    // We store the loan in service.
    this.loans$.pipe(
      map(loans => loans.find(loan => loan.id === loanId)),
      tap(x => console.log(x))).subscribe(loan => {
      this.service.loan = loan;
      this.router.navigate(['admin/loans', loanId]);
    });
  }

}
