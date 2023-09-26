import {Component, OnDestroy, OnInit} from '@angular/core';
import {Game} from '../../../model/game.model';
import {GameService} from '../game.service';
import {ImageService} from '../../../shared/services/image.service';
import {Router} from '@angular/router';
import {DeletionHandlerService} from '../../../shared/services/deletion-handler.service';
import {ModelEnum} from '../../../model/enum/model.enum';
import {EDITION, WrapperService} from '../../../shared/services/wrapper.service';
import {Image} from '../../../model/image.model';
import {Subscription} from 'rxjs';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit, OnDestroy {

  game: Game;
  numberOfPlayers: string;
  limitAge: string;
  areRulesDisplayed: boolean;
  private subscription: Subscription;

  constructor(private service: GameService,
              private imageService: ImageService,
              private router: Router,
              private deletionHandlerService: DeletionHandlerService,
              private wrapperService: WrapperService) {
  }

  ngOnInit(): void {
    this.subscription = this.service.detailedGame$
      .pipe(map((game: Game) => {
        this.game = game;
        console.log(game);
      })).subscribe(() => {
        this.setUpDisplay();
      });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  onBack(): void {
    this.router.navigate(['/admin/editor/games/', this.game.id]);
  }

  onDelete(): void {
    this.service.deleteThenFetchAll(this.game.id);
    this.router.navigate(['/admin/editor/games']);
  }

  onOpenConfirm(): void {
    this.deletionHandlerService.callModal(ModelEnum.GAME, this.game, false)
      .then(value => {
        if (value === 'Ok click') {
          this.onDelete();
        }
      })
      .catch(err => console.log(err));
  }

  private setUpDisplay(): void {
    this.numberOfPlayers = this.service.buildPLayers(this.game.minNumberOfPlayer, this.game.maxNumberOfPlayer);
    this.limitAge = this.service.buildAge(this.game.minAge, this.game.maxAge, this.game.minMonth);
    this.areRulesDisplayed = false;
  }

  toggleRuleDisplay(): void {
    this.areRulesDisplayed = !this.areRulesDisplayed;
  }

  onEdit(): void {
    this.wrapperService.entity = 'Jeux';
    this.wrapperService.mode = EDITION;
    this.router.navigate(['admin/locked-mode/games/' + this.game.id + '/edit']);
  }
}
