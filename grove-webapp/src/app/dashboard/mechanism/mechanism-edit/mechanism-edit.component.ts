import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {MechanismService} from '../mechanism.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Mechanism} from '../../../model/mechansim.model';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';
import {MechanismDataService} from '../mechanism-data.service';

@Component({
  selector: 'app-mechanism-edit',
  templateUrl: './mechanism-edit.component.html',
  styleUrls: ['./mechanism-edit.component.css']
})
export class MechanismEditComponent implements OnInit {
  private subscription: Subscription;
  private editMode: boolean;
  private id: number;
  mechanismForm: UntypedFormGroup;
  label: string;

  constructor(private mechanismsService: MechanismService,
              private mechanismsDataService: MechanismDataService,
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
    const name = 'name';
    const newMechanism = new Mechanism(
      this.mechanismForm.value[name]
    );

    if (this.editMode) {
      this.mechanismsDataService
        .editMechanism(this.id, newMechanism)
        .subscribe(mechanism => {
          this.mechanismsService.updateMechanisms(mechanism);
        });
    } else {
      this.mechanismsDataService
        .addMechanism(newMechanism)
        .subscribe(mechanism => {
          this.mechanismsService.updateMechanisms(mechanism);
        });
    }
    this.onCancel();
  }

  onCancel(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }


  private initFrom(): void {
    let mechanismName = '';


    if (this.editMode) {
      const mechanism: Mechanism = this.mechanismsService.getMechanismById(this.id);
      mechanismName = mechanism.title;
      this.label = 'Édition du mécanisme \"' + mechanismName + '\"';
    } else {
      this.label = 'Création d\'un mécanisme';
    }

    this.mechanismForm = new UntypedFormGroup({
      'name': new UntypedFormControl(mechanismName, [
        Validators.required,
        Validators.maxLength(50),
        this.nameAlreadyExists.bind(this)])
    });
  }


  nameAlreadyExists(control: UntypedFormControl): { [s: string]: boolean } {
    /* We need spit the case edit mode or not to allow save the current edited name */
    if (this.mechanismsService.getExistingMechanisms().indexOf(control.value.toLowerCase().trim()) !== -1) {
      return {'nameAlreadyExists': true};
    }
    return null;
  }
}
