import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../environments/environment';
import {GameCopy} from '../../../../model/game-copy.model';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GameCopiesService {
  readonly apiUri: string;
  copy: GameCopy | null = null;
  isEdit: boolean;

  constructor(private http: HttpClient) {
    this.apiUri = environment.apiUri;
    this.isEdit = false;
  }

  fetchById(id: number): Observable<GameCopy> {
    return this.http
      .get<GameCopy>(this.apiUri + '/api/admin/game-copies/' + id)
      .pipe(map((copy: GameCopy) => this.copy = copy));
  }

  edit(id: number, gc: GameCopy): Observable<GameCopy> {
    return this.http
      .put<GameCopy>(this.apiUri + '/api/admin/game-copies/' + id, gc)
      .pipe(map((copy: GameCopy) => this.copy = copy));
  }

  save(gc: GameCopy): Observable<GameCopy> {
    return this.http
      .post<GameCopy>(this.apiUri + '/api/admin/game-copies', gc)
      .pipe(map((copy: GameCopy) => this.copy = copy));
  }

  delete(copy: GameCopy): Observable<any> {
    return this.http
      .delete(this.apiUri + '/api/admin/game-copies/' + copy.id);
  }
}
