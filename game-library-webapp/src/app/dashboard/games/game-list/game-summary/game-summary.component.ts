import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {GameService} from '../../game.service';
import {Page} from '../../../../model/page.model';
import {GameOverview} from '../../../../model/game-overview.model';
import {ImageService} from '../../../../shared/services/image.service';
import {Image} from '../../../../model/image.model';
import {environment} from '../../../../../environments/environment';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-summary.component.html',
  styleUrls: ['./game-summary.component.css']
})
export class GameSummaryComponent implements OnInit, OnDestroy {
  game: GameOverview;
  image: Image;
  private paramId: number;
  private subscription: Subscription;
  numberOfPlayers: string;
  limitAge: string;
  filePrefix: string;

  constructor(private service: GameService,
              private imageService: ImageService,
              private route: ActivatedRoute,
              private router: Router) {
    this.filePrefix = environment.filePrefix;
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      const id = 'id';
      this.paramId = +params[id];
      this.game = this.service.getGameOverviewById(+this.paramId);
      this.fetchFirstImage();
    });
    this.subscription = this.service.pageChanged
      .subscribe((page: Page<GameOverview>) => {
        this.game = page.content.find(game => game.id === this.paramId);
      });
    this.numberOfPlayers = this.service.buildPLayers(this.game.minNumberOfPlayer, this.game.maxNumberOfPlayer);
    this.limitAge = this.service.buildAge(this.game.minAge, this.game.maxAge, this.game.minMonth);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onViewDetail(): void {
    this.router.navigate(['/admin/editor/games/detail/' + this.paramId]);
  }

  fetchFirstImage(): void {
    this.image = new Image();
    if (!(this.game.imageIds.length === 0)) {
      this.imageService
        .fetchImage(this.game.imageIds[0])
        .subscribe(data => {
          this.image.id = data.id;
          this.image.content = this.filePrefix + data.content;
        });
    }
  }
}

