import {Component, OnDestroy, OnInit} from '@angular/core';
import {Creator} from '../../../model/creator.model';
import {CreatorService} from '../creator.service';
import {CreatorDataService} from '../creator-data.service';
import {ConfigurationService} from '../../configuration/configuration.service';
import {Page} from '../../../model/page.model';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-creator-list',
  templateUrl: './creator-list.component.html',
  styleUrls: ['./creator-list.component.css']
})
export class CreatorListComponent implements OnInit, OnDestroy {
  creators: Creator[];
  private subscription: Subscription;
  totalElements: number;
  pageSize: number;
  page: number;
  filterForm: UntypedFormGroup;

  constructor(private creatorService: CreatorService,
              private creatorDataService: CreatorDataService,
              private configurationService: ConfigurationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.fetchCreators();
    this.subscription = this.creatorService.pagedCreatorsChanged.subscribe((pagedCreators: Page<Creator>) => {
      this.creators = pagedCreators.content.slice();
      this.totalElements = pagedCreators.totalElements;
    });
    this.initForm();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onRefreshList(): void {
    this.fetchCreators();
    this.router.navigate(['/admin/editor/creators']);
  }

  onPageChange(): void {
    this.fetchCreators(this.page);
    this.router.navigate(['/admin/editor/creators']);
  }


  onFilter(): void {
    this.fetchCreators(0, this.filterForm.value.keyword);
    this.initForm();
    this.router.navigate(['/admin/editor/creators']);
  }

  onDelete(): void {
    this.initForm();
  }

  private fetchCreators(page?: number, keyword?: string): void {
    this.creatorDataService.fetchCreators(page, keyword).subscribe((pagedCreators: Page<Creator>) => {
      this.page = pagedCreators.pageable.pageNumber + 1;
      this.totalElements = pagedCreators.totalElements;
      this.pageSize = this.configurationService.getNumberOfElements();
    });
  }

  private initForm(): void {
    this.filterForm = new UntypedFormGroup({
      'keyword': new UntypedFormControl('', [Validators.required, Validators.maxLength(50)])
    });
  }
}
