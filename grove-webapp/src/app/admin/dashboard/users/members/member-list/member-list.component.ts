import {Component, OnInit} from '@angular/core';
import {AccountService} from '../account.service';
import {Observable} from 'rxjs';
import {Account} from '../../../../../model/account.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-member-list',
  templateUrl: './member-list.component.html',
  styleUrls: ['./member-list.component.css']
})
export class MemberListComponent implements OnInit {

  accounts$: Observable<Account[]>;

  constructor(private accountService: AccountService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.accounts$ = this.accountService.fetchAll();
  }

  onNavigateToDetail(id: number): void {
    this.router.navigate(['admin/members', id]);
  }
}
