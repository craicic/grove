import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Observable} from 'rxjs';
import {GameService} from './game.service';
import {Game} from '../../../../model/game.model';

@Injectable({providedIn: 'root'})
export class GameResolver  {
  constructor(private service: GameService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Game> | Promise<Game> | Game {
    const id = 'id';
    console.log('Resolver triggered with param ' + route.params[id]);
    return this.service.fetchGameById(route.params[id]);
  }
}
