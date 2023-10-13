import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Observable} from 'rxjs';
import {GameService} from './game.service';
import {Page} from '../../../../model/page.model';
import {GameOverview} from '../../../../model/game-overview.model';

@Injectable({providedIn: 'root'})
export class GameOverviewResolver  {
  constructor(private service: GameService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Page<GameOverview>> | Promise<Page<GameOverview>> | Page<GameOverview> {

    return this.service.fetchGames();
  }
}
