import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {GameService} from '../../dashboard/games/game.service';
import {NAV, WrapperService} from '../../shared/services/wrapper.service';

@Component({
  selector: 'app-game-edit-wrapper',
  templateUrl: './game-edit-wrapper.component.html',
  styleUrls: ['./game-edit-wrapper.component.css']
})
export class GameEditWrapperComponent implements OnInit, OnDestroy {

  constructor(private router: Router,
              private service: GameService,
              private wrapperService: WrapperService) {
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.wrapperService.ngOnDestroy();
  }

  onBack(): void {
    this.wrapperService.mode = NAV;
    this.service.game ? this.router.navigate(['/admin/lib/games/detail', this.service.game.id])
      : this.router.navigate(['/admin/lib/games']);
  }
}
