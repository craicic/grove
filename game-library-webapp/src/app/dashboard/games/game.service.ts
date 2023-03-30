import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ConfigurationService} from '../configuration/configuration.service';
import {Page} from '../../model/page.model';
import {tap} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {GameOverview} from '../../model/game-overview.model';
import {Game} from '../../model/game.model';

@Injectable({providedIn: 'root'})
export class GameService {
  apiUri: string;
  game: Game;
  games: GameOverview[];
  page: Page<GameOverview> = {};
  pageChanged: Subject<Page<GameOverview>> = new Subject<Page<GameOverview>>();
  detailedGame$: BehaviorSubject<Game> = new BehaviorSubject<Game>(null);

  constructor(private http: HttpClient,
              private config: ConfigurationService) {
    this.apiUri = environment.apiUri;
  }

  /* ============================================== REST API METHODS =================================================================== */
  /** Get paged overview games */
  fetchGames(page?: number, keyword?: string): Observable<Page<GameOverview>> {
    console.log('fetchGames called!!');
    if (!page) {
      page = 0;
    }
    let keywordParam = '';
    if (keyword) {
      keywordParam = '&search=' + keyword.toLowerCase();
    }
    const size = this.config.getNumberOfElements();
    const params = '?page=' + page + '&size=' + size + '&sort=id' + keywordParam;
    return this.http
      .get<Page<GameOverview>>(this.apiUri + '/admin/games/page/overview' + params, {responseType: 'json'})
      .pipe(
        tap(
          pagedGameOverviews => {
            this.page = pagedGameOverviews;
          }
        )
      );
  }

  /** Get a game by id */
  fetchGameById(id: number): Observable<Game> {
    return this.http
      .get<Game>(this.apiUri + '/admin/games/' + id, {responseType: 'json'})
      .pipe(tap(game => {
        console.log('updating games');
        this.detailedGame$.next(game);
        this.game = game;
      }));
  }

  deleteThenFetchAll(id: number): void {
  }

  /** */
  fetchNames(): Observable<string[]> {
    return this.http
      .get<string[]>(this.apiUri + '/admin/games/names', {responseType: 'json'});
  }

  /** Edit the game via PUT request */
  editGame(id: number, game: Game): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + id, game, {responseType: 'json'});
  }

  /** Attach the category to the game */
  addCategory(gameId: number, categoryId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/add-category/' + categoryId, null, {responseType: 'json'});
  }

  /** Remove the category to the game */
  unlinkCategory(gameId: number, categoryId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/unlink-category/' + categoryId, {responseType: 'json'});
  }

  /** Attach the mechanism to the game */
  addMechanism(gameId: number, mechanismId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/add-mechanism/' + mechanismId, null, {responseType: 'json'});
  }

  /** Remove the mechanism to the game */
  unlinkMechanism(gameId: number, mechanismId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/unlink-mechanism/' + mechanismId, {responseType: 'json'});
  }

  /** Attach the creator to the game */
  addCreator(gameId: number, creatorId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/add-creator/' + creatorId, null, {responseType: 'json'});
  }

  /** Remove the creator to the game */
  unlinkCreator(gameId: number, creatorId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/unlink-creator/' + creatorId, {responseType: 'json'});
  }

  addProductLine(gameId: number, lineId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/add-product-line/' + lineId, null, {responseType: 'json'});

  }

  /** Remove the product line to the game */
  unlinkProductLine(gameId: number, lineId: number): Observable<Game> {
    return this.http
      .put<Game>(this.apiUri + '/admin/games/' + gameId + '/unlink-product-line/' + lineId, {responseType: 'json'});
  }

  /* ================================================ OTHER METHODS ==================================================================== */
  /** sets the page to the debut value */
  initPage(): void {
    this.pageChanged.next(this.page);
  }

  /** Updates the paged object as well as notifier the Subject a change occurred */
  updatePage(): void {
    this.pageChanged.next(this.page);
  }

  /** finds and return the game with the given id */
  getGameOverviewById(id: number): GameOverview {
    return this.page.content.find(game => game.id === id);
  }

  /** finds and return the last stored game */
  getDetailedGame(): Game {
    return this.game;
  }

  /** Update the behavior subject game */
  updateDetailedGame(game: Game): void {
    this.detailedGame$.next(game);
  }

  /** Get min and max numbers of player then return a string */
  buildPLayers(min: number, max: number): string {
    let str = '...';
    if (min === 1 && max === 1) {
      str = 'Jeu solo';
    }
    if (max > 1 && max === min) {
      str = min.toString() + ' joueurs';
    } else if (max > min) {
      str = 'De ' + min.toString() + ' à ' + max.toString() + ' joueurs';
    } else if (max === 0) {
      str = 'À partir de ' + min.toString() + ' joueur(s)';
    }
    return str;
  }

  /** Get limit age then return the age range in a string */
  buildAge(minAge: number, maxAge: number, minMonth: number): string {
    let str = '';
    if (minAge === 0 && maxAge === 0 && minMonth === 0) {
      return str;
    } else if (maxAge === 0) {
      str = 'À partir de ';
      if (minAge > 1) {
        str += minAge.toString() + ' ans.';
      } else if (minAge === 1) {
        str += minAge.toString() + ' an.';
      } else if (minMonth >= 1) {
        str += minMonth.toString() + ' mois.';
      }
      return str;
    } else if (maxAge > 0) {
      str = 'De ';
      if (minAge > 0) {
        str += minAge.toString() + ' à ' + maxAge.toString() + ' ans.';
      } else if (minMonth > 0 && maxAge > 1) {
        str += minMonth.toString() + ' mois à ' + maxAge.toString() + ' ans.';
      } else if (minMonth > 0 && maxAge === 1) {
        str += minMonth.toString() + ' mois à ' + maxAge.toString() + ' an.';
      } else if (minAge === 0 && minMonth === 0) {
        str = 'Jusqu\'à ' + maxAge;
        maxAge === 1 ? str += ' an.' : str += ' ans';
      }
    }
    return str;
  }
}
