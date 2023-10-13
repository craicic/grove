import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {merge, Observable, OperatorFunction, Subject} from 'rxjs';
import {Creator} from '../../../../../../../model/creator.model';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';

@Component({
  selector: 'app-creator-picker',
  templateUrl: './creator-picker.component.html',
  styleUrls: ['./creator-picker.component.css']
})
export class CreatorPickerComponent implements OnInit {

  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();
  @Output()
  backEvent = new EventEmitter<string>();

  @Input()
  allCreators: Creator[] = [];
  allCreatorsFullName: string[] = [];
  @Input()
  currentGameCreators: Creator[] = [];
  currentGameCreatorsFullName: string[] = [];

  availableCreators: Creator[] = [];
  availableCreatorsFullName: string[] = [];

  form: UntypedFormGroup;

  constructor() {
  }

  ngOnInit(): void {
    this.convertToFullNameString();
    this.availableCreatorsFullName = this.allCreatorsFullName.filter(element => !this.currentGameCreatorsFullName.includes(element));
    this.form = new UntypedFormGroup({
      'creatorField': new UntypedFormControl('', [
          Validators.required,
          this.creatorAvailable.bind(this)
        ]
      )
    });
  }

  onSubmit(): void {
    const creatorRetrieved = this.retrieveCreator(this.form.get('creatorField').value as string);
    /* this event remove the 'add mode', and triggers the service procedure to attach this creator to current game */
    this.backEvent.emit(creatorRetrieved);
  }

  onBack(): void {
    this.form.patchValue({creatorField: null});
    this.backEvent.emit(null);
  }

  search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.availableCreatorsFullName
        : this.availableCreatorsFullName.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  };

  private retrieveCreator(fullName: string): string {
    return this.availableCreatorsFullName.find(c => c === fullName);
  }

  creatorAvailable(control: UntypedFormControl): { [s: string]: boolean } {
    return !this.availableCreatorsFullName.includes(control.value) ? {'notAvailableCreator': true} : null;
  }

  convertToFullNameString(): void {
    this.allCreators.forEach(c => this.allCreatorsFullName.push(c.firstName + ' ' + c.lastName));
    this.currentGameCreators.forEach(c => this.currentGameCreatorsFullName.push(c.firstName + ' ' + c.lastName));
  }
}
