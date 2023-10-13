import {Component, OnDestroy, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {AccountService} from '../account.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Account} from '../../../../../model/account.model';

@Component({
  selector: 'app-member-new',
  templateUrl: './member-new.component.html',
  styleUrls: ['./member-new.component.css']
})
export class MemberNewComponent implements OnInit, OnDestroy {

  form: UntypedFormGroup;
  nameGroupSub: Subscription;

  constructor(private accountService: AccountService,
              private route: ActivatedRoute,
              private router: Router) {
    this.form = new UntypedFormGroup({
      'name': new UntypedFormGroup({
          'firstname': new UntypedFormControl('', [Validators.maxLength(128)]),
          'lastname': new UntypedFormControl('', [Validators.maxLength(128)])
        }
      ),
      'email': new UntypedFormControl('', [Validators.maxLength(255), Validators.email]),
      'username': new UntypedFormControl('', [Validators.maxLength(255), Validators.required])
    });
  }

  ngOnInit(): void {
    this.nameGroupSub = this.form.get('name').valueChanges.subscribe(() => {
      const nameObject = this.form.get('name').value;
      const separator = (nameObject.firstname !== '' && nameObject.lastname !== '') ? '-' : '';
      this.form.get('username').patchValue(nameObject.firstname + separator + nameObject.lastname);
    });
  }

  ngOnDestroy(): void {
    this.nameGroupSub.unsubscribe();
  }

  onSubmit(): void {
    let accountId: number;
    this.accountService.saveAndStoreAccount(new Account(this.form.value)).subscribe(account => {
      accountId = account.id;
      this.router.navigate(['/admin/members', accountId]);
    });
  }

  onBack(): void {
    this.router.navigate(['/admin/members']);
  }
}
