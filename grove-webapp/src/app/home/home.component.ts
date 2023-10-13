import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../auth/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(private authService: AuthenticationService) {
  }

  authenticated(): boolean {
    return this.authService.authenticated;
  }
}
