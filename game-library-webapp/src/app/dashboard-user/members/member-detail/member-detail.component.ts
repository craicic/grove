import {Component, OnInit} from '@angular/core';
import {AccountService} from '../account.service';
import {Account} from '../../../model/account.model';
import {Observable, of, Subject} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';

@Component({
  selector: 'app-member-detail',
  templateUrl: './member-detail.component.html',
  styleUrls: ['./member-detail.component.css']
})
export class MemberDetailComponent implements OnInit {

  account$: Observable<Account>;
  loadingError$ = new Subject<boolean>();
  paramId: number;

  constructor(private accountService: AccountService,
              private route: ActivatedRoute) {
    const id = 'id';
    this.paramId = this.route.snapshot.params[id];
  }

  ngOnInit(): void {
    if (this.accountService.account && this.accountService.account.id === this.paramId) {
      this.account$ = of(this.accountService.account);
    } else {
      this.account$ = this.accountService.fetchById(this.paramId).pipe(
        tap(a => console.log(a.contact)),
        catchError((error) => {
          // it's important that we log an error here.
          // Otherwise you won't see an error in the console.
          console.error('error loading account of id:' + this.paramId, error);
          this.loadingError$.next(true);
          return of<Account>();
        })
      );
    }
  }

}
