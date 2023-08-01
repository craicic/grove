import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {ConfigurationService} from '../../configuration/configuration.service';
import {Router} from '@angular/router';
import {Page} from '../../../model/page.model';
import {Publisher} from '../../../model/publisher.model';
import {PublisherService} from '../publisher.service';
import {PublisherDataService} from '../publisher-data.service';

@Component({
  selector: 'app-publisher-list',
  templateUrl: './publisher-list.component.html',
  styleUrls: ['./publisher-list.component.css']
})
export class PublisherListComponent implements OnInit, OnDestroy {
  publishers: Publisher[];
  private subscription: Subscription;
  totalElements: number;
  pageSize: number;
  page: number;
  filterForm: UntypedFormGroup;

  constructor(private publishersService: PublisherService,
              private publishersDataService: PublisherDataService,
              private configurationService: ConfigurationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.fetchPublishers();
    this.subscription = this.publishersService.pagedPublishersChanged.subscribe((pagedPublishers: Page<Publisher>) => {
      this.publishers = pagedPublishers.content.slice();
      this.totalElements = pagedPublishers.totalElements;
    });
    this.initForm();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onRefreshList(): void {
    this.fetchPublishers();
    this.router.navigate(['/admin/publishers']);
  }

  onPageChange(): void {
    this.fetchPublishers(this.page);
    this.router.navigate(['/admin/publishers']);
  }


  onFilter(): void {
    this.fetchPublishers(0, this.filterForm.value.keyword);
    this.initForm();
    this.router.navigate(['/admin/publishers']);
  }

  onDelete(): void {
    this.initForm();
  }

  private fetchPublishers(page?: number, keyword?: string): void {
    this.publishersDataService.fetchPublishers(page, keyword).subscribe((pagedPublishers: Page<Publisher>) => {
      this.page = pagedPublishers.pageable.pageNumber + 1;
      this.totalElements = pagedPublishers.totalElements;
      this.pageSize = this.configurationService.getNumberOfElements();
    });
  }

  private initForm(): void {
    this.filterForm = new UntypedFormGroup({
      'keyword': new UntypedFormControl('', [Validators.required, Validators.maxLength(50)])
    });
  }
}
