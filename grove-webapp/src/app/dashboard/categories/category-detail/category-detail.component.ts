import {Component, OnDestroy, OnInit} from '@angular/core';
import {Category} from '../../../model/category.model';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {DeletionHandlerService} from '../../../shared/services/deletion-handler.service';
import {CategoryService} from '../category.service';
import {Page} from '../../../model/page.model';
import {ModelEnum} from '../../../model/enum/model.enum';

@Component({
  selector: 'app-category-detail',
  templateUrl: './category-detail.component.html',
  styleUrls: ['./category-detail.component.css']
})
export class CategoryDetailComponent implements OnInit, OnDestroy {
  category: Category;

  private paramId: number;
  private subscription: Subscription;

  constructor(private service: CategoryService,
              private route: ActivatedRoute,
              private router: Router,
              private deletionHandlerService: DeletionHandlerService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      const id = 'id';
      this.paramId = +params[id];
      this.category = this.service.getCategoryById(+this.paramId);
    });
    this.subscription = this.service.pageChanged.subscribe((page: Page<Category>) => {
      this.category = page.content.find(category => category.id === this.paramId);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onEdit(): void {
    this.router.navigate(['/admin/lib/categories/', this.category.id, 'edit']);
  }

  onDelete(): void {
    this.service.deleteThenFetchAll(this.category.id);
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  onOpenConfirm(): void {
    this.deletionHandlerService.callModal(ModelEnum.CATEGORY, this.category, false)
      .then(value => {
        if (value === 'Ok click') {
          this.onDelete();
        }
      })
      .catch(err => console.log(err));
  }
}
