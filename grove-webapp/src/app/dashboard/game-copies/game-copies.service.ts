import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {GameCopy} from '../../model/game-copy.model';
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

  editCopy(id: number, gc: GameCopy): Observable<GameCopy> {
    return this.http.put<GameCopy>(this.apiUri + '/api/admin/game-copies/' + id, gc);
  }

  saveCopy(gc: GameCopy): Observable<GameCopy> {
    return this.http.post<GameCopy>(this.apiUri + '/api/admin/game-copies', gc);
  }
}
