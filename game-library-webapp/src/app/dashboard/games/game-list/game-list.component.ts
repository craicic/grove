import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {ConfigurationService} from '../../configuration/configuration.service';
import {Router} from '@angular/router';
import {GameService} from '../game.service';
import {Page} from '../../../model/page.model';
import {GameOverview} from '../../../model/game-overview.model';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit {
  filterForm: FormGroup;
  private subscription: Subscription;

  /* Pagination */
  games: GameOverview[];
  totalElements: number;
  pageSize: number;
  page: number;

  constructor(private service: GameService,
              private configurationService: ConfigurationService,
              private router: Router) {
  }

  ngOnInit(): void {
    /* the resolver load paged games then ... */
    this.initForm();
    this.subscription = this.service.pageChanged.subscribe((page: Page<GameOverview>) => {
      this.games = page.content.slice();
      this.totalElements = page.totalElements;
    });
    this.service.initPage();
  }

  onFilter(): void {
    this.service
      .fetchGames(0, this.filterForm.value.keyword)
      .subscribe(() => this.service.updatePage());
    this.initForm();
    this.router.navigate(['/admin/editor/games/list']);
  }

  onRefreshList(): void {
    this.service
      .fetchGames()
      .subscribe(() => this.service.updatePage());
    this.router.navigate(['/admin/editor/games/list']);
  }

  onDelete(): void {
    this.initForm();
  }

  onPageChange(pageNumber): void {
    if (!Number.isNaN(pageNumber)) {
      this.service.fetchGames(this.page);
      this.router.navigate(['/admin/editor/games/list']);
    }
  }

  private initForm(): void {
    this.filterForm = new FormGroup({
      'keyword': new FormControl('', [Validators.required, Validators.maxLength(50)])
    });
  }

}
