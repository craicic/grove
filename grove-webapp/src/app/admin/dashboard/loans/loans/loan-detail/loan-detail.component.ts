import {Component, OnInit} from '@angular/core';
import {Loan} from '../../../../../model/loan.model';
import {LoanService} from '../loan.service';

@Component({
  selector: 'app-loan-detail',
  templateUrl: './loan-detail.component.html',
  styleUrls: ['./loan-detail.component.css']
})
export class LoanDetailComponent implements OnInit {

  loan: Loan;

  constructor(private service: LoanService) {
  }

  ngOnInit(): void {
    this.loan = this.service.loan;
  }

  onCloseLoan(loanId: number): void {
    this.service.closeLoan(loanId).subscribe(loan => {
      this.loan = loan;
      this.service.loan = loan;
    });
  }

}
