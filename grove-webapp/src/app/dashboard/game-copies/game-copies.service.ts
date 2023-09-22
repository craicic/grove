import {Injectable} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {GameCopy} from '../../model/game-copy.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameCopiesService {
  apiUri: string;
  copy: GameCopy | null = null;
  isEdit: boolean;
  selected: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient) {
    this.apiUri = environment.apiUri;
    this.isEdit = false;
  }

  fetchById(id: number): Observable<GameCopy> {
    return this.http.get<GameCopy>(this.apiUri + '/api/admin/game-copies/' + id);
  }

  wasReloaded(): void {
    this.isEdit = false;
  }
}
