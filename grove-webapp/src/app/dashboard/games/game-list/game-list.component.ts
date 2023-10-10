import {Component, OnDestroy, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {GameService} from '../game.service';
import {Page} from '../../../model/page.model';
import {GameOverview} from '../../../model/game-overview.model';
import {ImageService} from '../../../shared/services/image.service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit, OnDestroy {
  filterForm: UntypedFormGroup;
  private subscription: Subscription;
  private imageSub: Subscription;

  /* Pagination */
  games: GameOverview[];
  totalElements: number;
  pageSize: number;
  page: number;

  constructor(private service: GameService,
              private imageService: ImageService,
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

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    if (this.imageSub) {
      this.imageSub.unsubscribe();
    }
  }


  onFilter(): void {
    this.service
      .fetchGames(0, this.filterForm.value.keyword)
      .subscribe(() => this.service.updatePage());
    this.initForm();
    this.router.navigate(['/admin/editor/games']);
  }

  onRefreshList(): void {
    this.service
      .fetchGames()
      .subscribe(() => this.service.updatePage());
    this.router.navigate(['/admin/editor/games']);
  }

  onDelete(): void {
    this.initForm();
  }

  onPageChange(pageNumber): void {
    if (!Number.isNaN(pageNumber)) {
      this.service.fetchGames(this.page);
      this.router.navigate(['/admin/editor/games']);
    }
  }

  private initForm(): void {
    this.filterForm = new UntypedFormGroup({
      'keyword': new UntypedFormControl('', [Validators.required, Validators.maxLength(50)])
    });
  }

  onDetail(id: number): void {
    this.imageSub = this.imageService.fetchImages(id).subscribe();
  }
}
