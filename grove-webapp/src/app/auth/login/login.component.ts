import {Component} from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  constructor(private auth: AuthenticationService, private router: Router) {
  }

  authenticated(): boolean {
    return this.auth.authenticated;
  }

  login(): void {
    console.log(this.form.value.username);
    this.auth.authenticate(
      {username: this.form.value.username, password: this.form.value.password},
      () => {
        this.router.navigate(['']);
      }
    );
  }

}
