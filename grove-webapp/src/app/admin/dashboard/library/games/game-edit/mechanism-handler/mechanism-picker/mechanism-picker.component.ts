import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {merge, Observable, OperatorFunction, Subject} from 'rxjs';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {GameService} from '../../../game.service';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import {Mechanism} from '../../../../../../../model/mechansim.model';

@Component({
  selector: 'app-mechanism-picker',
  templateUrl: './mechanism-picker.component.html',
  styleUrls: ['./mechanism-picker.component.css']
})
export class MechanismPickerComponent implements OnInit {


  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  @Output()
  backEvent = new EventEmitter<Mechanism>();

  @Input()
  mechanisms: Mechanism[] = [];
  @Input()
  gameMechanisms: Mechanism[] = [];

  gameIds: number[] = [];
  availableMechanisms: Mechanism[];
  availableMechanismsTitle: string[] = [];

  form: UntypedFormGroup;

  ngOnInit(): void {
    this.gameMechanisms.forEach(t => this.gameIds.push(t.id));
    this.availableMechanisms = this.mechanisms.filter(t => !this.gameIds.includes(t.id));
    this.availableMechanisms.forEach(t => this.availableMechanismsTitle.push(t.title));
    this.form = new UntypedFormGroup({
      'mechanismField': new UntypedFormControl('', [
          Validators.required,
          this.mechanismAvailable.bind(this)
        ]
      )
    });
  }

  onSubmit(): void {
    const mechanismRetrieved = this.retrieveMechanism(this.form.get('mechanismField').value as string);
    /* this event remove the 'add mode', and triggers the service procedure to attach this mechanism to current game */
    this.backEvent.emit(mechanismRetrieved);
  }

  onBack(): void {
    this.form.patchValue({mechanismField: null});
    this.backEvent.emit(null);
  }

  search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.availableMechanismsTitle
        : this.availableMechanismsTitle.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  };

  private retrieveMechanism(title: string): Mechanism {
    return this.availableMechanisms.find(c => c.title === title);
  }

  mechanismAvailable(control: UntypedFormControl): { [s: string]: boolean } {
    return !this.availableMechanismsTitle.includes(control.value) ? {'notAvailableMechanism': true} : null;
  }

}
