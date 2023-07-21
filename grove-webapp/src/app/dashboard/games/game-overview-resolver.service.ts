import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {GameService} from './game.service';
import {Page} from '../../model/page.model';
import {GameOverview} from '../../model/game-overview.model';

@Injectable({providedIn: 'root'})
export class GameOverviewResolver implements Resolve<Page<GameOverview>> {
  constructor(private service: GameService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Page<GameOverview>> | Promise<Page<GameOverview>> | Page<GameOverview> {

    return this.service.fetchGames();
  }
}
