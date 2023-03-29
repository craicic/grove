import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {GameService} from '../../game.service';
import {Page} from '../../../../model/page.model';
import {GameOverview} from '../../../../model/game-overview.model';
import {ImageService} from '../../../../shared/services/image.service';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-summary.component.html',
  styleUrls: ['./game-summary.component.css']
})
export class GameSummaryComponent implements OnInit, OnDestroy {
  game: GameOverview;
  dataUri;

  imageData: string;
  private paramId: number;
  private subscription: Subscription;
  numberOfPlayers: string;
  limitAge: string;

  constructor(private service: GameService,
              private sanitizer: DomSanitizer,
              private imageService: ImageService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      const id = 'id';
      this.paramId = +params[id];
      this.game = this.service.getGameOverviewById(+this.paramId);
    });
    this.subscription = this.service.pageChanged
      .subscribe((page: Page<GameOverview>) => {
        this.game = page.content.find(game => game.id === this.paramId);
        this.dataUri = this.game.imageIds[0];
      });
    this.numberOfPlayers = this.service.buildPLayers(this.game.minNumberOfPlayer, this.game.maxNumberOfPlayer);
    this.limitAge = this.service.buildAge(this.game.minAge, this.game.maxAge, this.game.minMonth);
    this.imageService
      .fetchImage(this.game.imageIds[0])
      .subscribe(data => this.imageData = 'data:image/png;base64,' + data);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onViewDetail(): void {
    this.router.navigate(['/admin/editor/games/detail/' + this.paramId]);
  }
}

