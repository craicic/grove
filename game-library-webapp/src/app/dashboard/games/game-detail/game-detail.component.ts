import {Component, OnInit} from '@angular/core';
import {Game} from '../../../model/game.model';
import {GameService} from '../game.service';
import {ImageService} from '../../../shared/services/image.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DeletionHandlerService} from '../../../shared/services/deletion-handler.service';
import {ModelEnum} from '../../../model/enum/model.enum';
import {EDITION, WrapperService} from '../../../shared/services/wrapper.service';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit {

  game: Game;
  dataUri;
  dataUriArray: string[] = [];
  numberOfPlayers: string;
  limitAge: string;
  areRulesDisplayed: boolean;

  constructor(private service: GameService,
              private imageService: ImageService,
              private route: ActivatedRoute,
              private router: Router,
              private deletionHandlerService: DeletionHandlerService,
              private wrapperService: WrapperService) {
  }

  ngOnInit(): void {
    this.game = this.service.getDetailedGame();
    this.numberOfPlayers = this.service.buildPLayers(this.game.minNumberOfPlayer, this.game.maxNumberOfPlayer);
    this.limitAge = this.service.buildAge(this.game.minAge, this.game.maxAge, this.game.minMonth);
    this.loadAllImages();
    this.areRulesDisplayed = false;
  }

  onBack(): void {
    this.router.navigate(['/admin/editor/games/list/', this.game.id]);
  }

  onDelete(): void {
    this.service.deleteThenFetchAll(this.game.id);
    this.router.navigate(['/admin/editor/games/list']);
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

  private loadAllImages(): void {
    this.game.imageIds.forEach(id => {
      this.imageService
        .fetchImage(id)
        .subscribe(
          imageData => this.dataUriArray.push('data:image/png;base64,' + imageData)
        );
    });
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
