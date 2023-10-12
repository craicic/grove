import {Component, OnDestroy, OnInit} from '@angular/core';
import {Game} from '../../../model/game.model';
import {GameService} from '../game.service';
import {ImageService} from '../../../shared/services/image.service';
import {map} from 'rxjs/operators';
import {Subscription} from 'rxjs';
import {GameCopiesService} from '../../game-copies/game-copies.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-game-edit',
  templateUrl: './game-edit.component.html',
  styleUrls: ['./game-edit.component.css']
})
export class GameEditComponent implements OnInit, OnDestroy {
  game: Game;
  numberOfPlayers: string;
  limitAge: string;
  subscription: Subscription;
  imagesSubscription: Subscription;

  constructor(private service: GameService,
              private imageService: ImageService,
              private copyService: GameCopiesService,
              private router: Router) {
  }

  ngOnInit(): void {

    this.subscription = this.service.detailedGame$
      .pipe(map(game => {
        this.game = game;
        console.log(game);
      }))
      .subscribe(() => {
        this.numberOfPlayers = this.service.buildPLayers(this.game.minNumberOfPlayer, this.game.maxNumberOfPlayer);
        this.limitAge = this.service.buildAge(this.game.minAge, this.game.maxAge, this.game.minMonth);
      });

    this.imagesSubscription = this.imageService.images$.subscribe(i => {
      this.game.nbOfImages = i.length;
      this.service.updateDetailedGame(this.game);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.imagesSubscription.unsubscribe();
  }


  onEditCopy(): void {
    this.copyService.isEdit = true;
    // this.router.navigate(['/admin/lib/lock/games' + this.game.id + '/edit/copy/' + id]);
  }

  onNewCopy(): void {
    this.copyService.isEdit = false;
    this.router.navigate(['/admin/lib/lock/games' + this.game.id + '/edit/copy/new']);
  }
}
