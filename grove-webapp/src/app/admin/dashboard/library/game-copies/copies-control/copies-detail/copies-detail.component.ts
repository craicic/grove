import {Component, Input, OnInit} from '@angular/core';
import {GameCopy} from '../../../../../../model/game-copy.model';
import {Observable, of, Subject} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {GameCopiesService} from '../../game-copies.service';
import {GeneralStateEnum} from '../../../../../../model/enum/general-state.enum';

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
  loadingError$: Subject<boolean> = new Subject<boolean>();
  protected readonly GeneralStateEnum = GeneralStateEnum;

  constructor(private service: GameCopiesService) {
  }

  ngOnInit(): void {
    if (this.service.copy !== null) {
      this.copy$ = of(this.service.copy);
    } else {
      this.copy$ = this.service.fetchById(this.copy.id).pipe(
        tap(c => console.log(this.service.copy = c)),
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
}
