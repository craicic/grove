import {Component, OnDestroy, OnInit} from '@angular/core';
import {GameService} from '../../game.service';
import {CreatorService} from '../../../creators/creator.service';
import {BehaviorSubject, Observable, Subscription} from 'rxjs';
import {Creator} from '../../../../model/creator.model';
import {Game} from '../../../../model/game.model';
import {CreatorRoleEnum} from '../../../../model/enum/creator-role.enum';
import {concatMap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-creator-handler',
  templateUrl: './creator-handler.component.html',
  styleUrls: ['./creator-handler.component.css']
})
export class CreatorHandlerComponent implements OnInit, OnDestroy {
  allCreatorsName$: Observable<Creator[]>;
  gameCreators$: BehaviorSubject<Creator[]> = new BehaviorSubject<Creator[]>(null);
  private game: Game;
  addModeOn: boolean;
  subscription: Subscription;
  actualEnumType: typeof CreatorRoleEnum;

  constructor(private service: GameService,
              private creatorService: CreatorService,
              private router: Router,
              private route: ActivatedRoute) {
    this.actualEnumType = CreatorRoleEnum;
  }

  ngOnInit(): void {
    this.addModeOn = false;
    this.subscription = this.service.detailedGame$.subscribe(game => {
      this.gameCreators$.next(game.creators);
      this.game = game;
    });
    this.allCreatorsName$ = this.creatorService.fetchAllNames();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onAddMode(): void {
    this.addModeOn = true;
  }

  attachCreator($creatorFullName: string): void {
    this.addModeOn = false;
    this.creatorService
      .findByFullName($creatorFullName.toLowerCase().replace(' ', ''))
      .pipe(
        concatMap(creator => this.service.addCreator(this.game.id, creator.id))
      ).subscribe(game => this.service.detailedGame$.next(game));
  }

  onRemove(creatorId: number): void {
    this.service.unlinkCreator(this.game.id, creatorId).subscribe(game => this.service.detailedGame$.next(game));
  }

  onBack(): void {
    this.router.navigate(['./..'], {relativeTo: this.route});
  }
}
