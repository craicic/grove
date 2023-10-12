import {Component, OnDestroy, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Category} from '../../../model/category.model';
import {ConfigurationService} from '../../configuration/configuration.service';
import {Router} from '@angular/router';
import {CategoryService} from '../category.service';
import {PageCustom} from '../../../model/page-custom.model';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit, OnDestroy {
  filterForm: UntypedFormGroup;
  private subscription: Subscription;

  /* Pagination */
  categories: Category[];
  totalElements: number;
  pageSize: number;
  page: number;

  constructor(private service: CategoryService,
              private configurationService: ConfigurationService,
              private router: Router) {
  }

  ngOnInit(): void {

    /* the resolver load all categories then ... */
    this.initForm();
    this.subscription = this.service.pageChanged.subscribe((page: PageCustom<Category>) => {
      this.categories = page.content.slice();
      this.totalElements = page.content.length;
      this.page = page.pageNumber;
      this.pageSize = page.pageSize;
    });
    this.service.initPage();
  }


  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  onPageChange(): void {
    // this.router.navigate(['/admin/categories']);
  }

  onFilter(): void {
    this.service.filter(this.filterForm.get('keyword').value);
  }

  onRefreshFilter(): void {
    this.service.removeFilter();
    this.filterForm.reset();
  }

  onRefreshList(): void {
    this.service.fetchAllAndStore();
    this.service.updatePage();
    this.router.navigate(['/admin/lib/categories']);
  }

  onDelete(): void {
    this.filterForm.reset();
  }

  private initForm(): void {
    this.filterForm = new UntypedFormGroup({
      'keyword': new UntypedFormControl('', [Validators.required, Validators.maxLength(50)])
    });
  }


}
