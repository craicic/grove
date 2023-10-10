import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {UntypedFormControl, UntypedFormGroup, ValidationErrors, Validators} from '@angular/forms';
import {CategoryService} from '../category.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Category} from '../../../model/category.model';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.css']
})
export class CategoryEditComponent implements OnInit {
  private subscription: Subscription;
  private editMode: boolean;
  private id: number;
  categoryForm: UntypedFormGroup;
  label: string;

  constructor(private service: CategoryService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.subscription = this.route.params.subscribe(
      (params: Params) => {
        const id = 'id';
        this.id = +params[id];
        if (params[id]) {
          this.editMode = true;
        } else {
          this.editMode = false;
        }
        this.initFrom();
      }
    );
  }

  onSubmit(): void {
    const category = this.categoryForm.value;

    if (this.editMode) {
      this.service
        .edit(this.id, category);
    } else {
      this.service
        .add(category);
    }
    this.onCancel();
  }

  onCancel(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }


  private initFrom(): void {
    let categoryTitle = '';


    if (this.editMode) {
      const category: Category = this.service.getCategoryById(this.id);
      categoryTitle = category.title;
      this.label = 'Édition de la catégorie \"' + categoryTitle + '\"';
    } else {
      this.label = 'Création d\'une catégorie';
    }
    this.service.setLowerCasedAndTrimmedCategoryTitles();
    this.categoryForm = new UntypedFormGroup({
        'title': new UntypedFormControl(categoryTitle, [Validators.required, Validators.maxLength(50)]),
      },
      (!this.editMode) ?
        {validators: this.titlesExistValidator.bind(this)} :
        {validators: this.titlesExistEditModeValidator.bind(this)});
  }


  titlesExistValidator(control: UntypedFormControl): ValidationErrors | null {
    const currentTitle = control.get('title').value.toLowerCase().trim();
    const titles = this.service.getLowerCasedAndTrimmedCategoryTitles();
    if (titles.indexOf(currentTitle) !== -1) {
      return {titleAlreadyExists: true};
    }
    return null;
  }

  titlesExistEditModeValidator(control: UntypedFormControl): ValidationErrors | null {
    const currentTitle = control.get('title').value.toLowerCase().trim();
    const titles = this.service.getLowerCasedAndTrimmedCategoryTitles();
    if (titles.indexOf(currentTitle) !== -1 && currentTitle !== this.service.getCategoryById(this.id).title.toLowerCase().trim()) {
      return {titleAlreadyExists: true};
    }
    return null;
  }
}
