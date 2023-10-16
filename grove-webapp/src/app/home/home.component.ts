import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../auth/authentication.service';
import {GameService} from '../admin/dashboard/library/games/game.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authService: AuthenticationService,
              private gameService: GameService) {
  }

  ngOnInit(): void {
  }

  authenticated(): boolean {
    return this.authService.authenticated;
  }
}
