import {Component, OnDestroy, OnInit} from '@angular/core';
import {Game} from '../../../model/game.model';
import {GameService} from '../game.service';
import {ImageService} from '../../../shared/services/image.service';
import {map} from 'rxjs/operators';
import {Subscription} from 'rxjs';
import {Image} from '../../../model/image.model';
import {GameCopiesService} from '../../game-copies/game-copies.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-game-edit',
  templateUrl: './game-edit.component.html',
  styleUrls: ['./game-edit.component.css']
})
export class GameEditComponent implements OnInit, OnDestroy {
  image: Image;
  game: Game;
  numberOfPlayers: string;
  limitAge: string;
  subscription: Subscription;
  images: Image[];

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
    this.images = [];
    // this.imageService
    //   .fetchImage(1)
    //   .subscribe(data => {
    //     this.image = new Image();
    //     this.image.id = data.id;
    //     this.image.content = 'data:image/png;base64,' + data.content;
    //     this.images.push(this.image);
    //   });

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  // private loadAllImages(ids: number[]): void {
  //   this.images = [];
  //   ids.forEach(id => {
  //     this.imageService
  //       .fetchImage(id)
  //       .subscribe(data => {
  //         const image = new Image();
  //         image.id = data.id;
  //         image.content = 'data:image/png;base64,' + data.content;
  //         this.images.push(image);
  //       });
  //   });
  // }

  onEditCopy(): void {
    this.copyService.isEdit = true;
    // this.router.navigate(['/admin/locked-mode/games/' + this.game.id + '/edit/copy/' + id]);
  }

  onNewCopy(): void {
    this.copyService.isEdit = false;
    this.router.navigate(['/admin/locked-mode/games/' + this.game.id + '/edit/copy/new']);
  }
}
