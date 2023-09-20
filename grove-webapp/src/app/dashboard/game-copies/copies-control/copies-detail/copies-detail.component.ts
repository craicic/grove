import {Component, Input, OnInit} from '@angular/core';
import {GameCopy} from '../../../../model/game-copy.model';
import {Observable, of, Subject} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {Account} from '../../../../model/account.model';
import {error} from 'protractor';
import {GameCopiesService} from '../../game-copies.service';

@Component({
  selector: 'app-copies-detail',
  templateUrl: './copies-detail.component.html',
  styleUrls: ['./copies-detail.component.css']
})
export class CopiesDetailComponent implements OnInit {

  // copy id and code passed by parent
  @Input() copy: GameCopy;
  // full copy detail fetched by service
  copy$: Observable<GameCopy>;
  loadingError$ = new Subject<boolean>();


  constructor(private service: GameCopiesService) {
  }

  ngOnInit(): void {
    this.copy$ = this.service.fetchById(this.copy.id).pipe(
      tap(c => console.log(c)),
      catchError((e) => {
        // it's important that we log an error here.
        // Otherwise, you won't see an error in the console.
        console.error('error loading account of id:' + this.copy.id, e);
        this.loadingError$.next(true);
        return of<GameCopy>();
      })
    );
  }


}
