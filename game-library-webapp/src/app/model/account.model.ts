import {accountFormValueModel} from './account-form-value.model';
import {Contact} from './contact.model';

export class Account {

  constructor(userForm: accountFormValueModel) {
    if (userForm.email) {
      this.contact = new Contact('France', userForm.email);
    }
    this.firstName = userForm.name.firstname;
    this.lastName = userForm.name.lastname;
    this.username = userForm.username;
  }

  id?: number;
  firstName: string;
  lastName: string;
  username: string;
  renewalDate?: Date;
  membershipNumber?: string;
  contact?: Contact;
}
