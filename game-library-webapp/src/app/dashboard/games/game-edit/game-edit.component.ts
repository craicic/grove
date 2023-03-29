import {Component, OnDestroy, OnInit} from '@angular/core';
import {Game} from '../../../model/game.model';
import {GameService} from '../game.service';
import {ImageService} from '../../../shared/services/image.service';
import {map} from 'rxjs/operators';
import {Subscription} from 'rxjs';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-game-edit',
  templateUrl: './game-edit.component.html',
  styleUrls: ['./game-edit.component.css']
})
export class GameEditComponent implements OnInit, OnDestroy {

  game: Game;
  numberOfPlayers: string;
  limitAge: string;
  dataUriArray: SafeResourceUrl[] = null;
  subscription: Subscription;

  constructor(private service: GameService,
              private sanitizer: DomSanitizer,
              private imageService: ImageService) {
  }

  ngOnInit(): void {
    this.subscription = this.service.detailedGame$.pipe(map(game => this.game = game)).subscribe(() => {
      this.numberOfPlayers = this.service.buildPLayers(this.game.minNumberOfPlayer, this.game.maxNumberOfPlayer);
      this.limitAge = this.service.buildAge(this.game.minAge, this.game.maxAge, this.game.minMonth);
      this.dataUriArray ? console.log('loadAllImages was skipped') : this.loadAllImages();
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private loadAllImages(): void {
    this.dataUriArray = [];
    this.game.imageIds.forEach(id => {
      this.imageService
        .fetchImage(id)
        .subscribe(
          imageData => this.dataUriArray.push(this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64, ' + imageData))
        );
    });
  }
}
