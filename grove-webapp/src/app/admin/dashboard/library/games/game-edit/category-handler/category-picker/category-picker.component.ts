import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Category} from '../../../../../../../model/category.model';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {merge, Observable, OperatorFunction, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {GameService} from '../../../game.service';

@Component({
  selector: 'app-category-picker',
  templateUrl: './category-picker.component.html',
  styleUrls: ['./category-picker.component.css']
})
export class CategoryPickerComponent implements OnInit {

  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  @Output()
  backEvent = new EventEmitter<Category>();

  @Input()
  categories: Category[] = [];
  @Input()
  gameCategories: Category[] = [];
  gameIds: number[] = [];
  availableCategories: Category[];
  availableCategoriesTitle: string[] = [];

  form: UntypedFormGroup;

  constructor(private service: GameService) {
  }

  ngOnInit(): void {
    this.gameCategories.forEach(c => this.gameIds.push(c.id));
    this.availableCategories = this.categories.filter(c => !this.gameIds.includes(c.id));
    this.availableCategories.forEach(c => this.availableCategoriesTitle.push(c.title));
    this.form = new UntypedFormGroup({
      'categoryField': new UntypedFormControl('', [
          Validators.required,
          this.categoryAvailable.bind(this)
        ]
      )
    });
  }

  onSubmit(): void {
    const categoryRetrieved = this.retrieveCategory(this.form.get('categoryField').value as string);
    /* this event remove the 'add mode', and triggers the service procedure to attach this category to current game */
    this.backEvent.emit(categoryRetrieved);
  }

  onBack(): void {
    this.form.patchValue({categoryField: null});
    this.backEvent.emit(null);
  }

  search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.availableCategoriesTitle
        : this.availableCategoriesTitle.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  }

  private retrieveCategory(title: string): Category {
    return this.availableCategories.find(c => c.title === title);
  }

  categoryAvailable(control: UntypedFormControl): { [s: string]: boolean } {
    return !this.availableCategoriesTitle.includes(control.value) ? {'notAvailableCategory': true} : null;
  }
}
