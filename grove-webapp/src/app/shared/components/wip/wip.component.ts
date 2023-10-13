import { Component } from '@angular/core';
import {AuthenticationService} from '../../../auth/authentication.service';

@Component({
  selector: 'app-wip',
  templateUrl: './wip.component.html',
  styleUrls: ['./wip.component.css']
})
export class WipComponent {


  constructor(private authService: AuthenticationService) {
  }

  authenticated(): boolean {
    return this.authService.authenticated;
  }
}
