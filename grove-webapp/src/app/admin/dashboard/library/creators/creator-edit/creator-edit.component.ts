import {Component, OnInit} from '@angular/core';
import {CreatorService} from '../creator.service';
import {CreatorDataService} from '../creator-data.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {UntypedFormControl, UntypedFormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {Creator} from '../../../../../model/creator.model';
import {CreatorRoleEnum} from '../../../../../model/enum/creator-role.enum';
import {CountryDataService} from '../../../../../shared/services/country-data.service';

@Component({
  selector: 'app-creator-edit',
  templateUrl: './creator-edit.component.html',
  styleUrls: ['./creator-edit.component.css']
})
export class CreatorEditComponent implements OnInit {
  editMode: boolean;
  hasContact: boolean;
  private subscription: Subscription;
  private id: number;
  creatorForm: UntypedFormGroup;
  contactForm: UntypedFormGroup;
  rolesList: Array<string>;
  actualEnumType: typeof CreatorRoleEnum;
  label: string;

  constructor(private creatorsService: CreatorService,
              private creatorsDataService: CreatorDataService,
              private countryDataService: CountryDataService,
              private route: ActivatedRoute,
              private router: Router) {
  }


  ngOnInit(): void {
    this.hasContact = false;
    this.populateRoles();
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

  onAddContactForm(): void {
    this.hasContact = true;
    this.creatorForm.addControl('contact', this.contactForm);
  }

  onRemoveContactForm(): void {
    this.hasContact = false;
    this.creatorForm.removeControl('contact');
  }

  onSubmit(): void {
    const creator = this.creatorForm.value;

    if (this.editMode) {
      const storedCreator = this.creatorsService.getCreatorById(this.id);
      if (storedCreator
        && storedCreator.contact) {
        creator.contact.id = storedCreator.contact.id;
      }

      this.creatorsDataService.editCreator(this.id, creator);
    } else {
      this.creatorsDataService.addCreator(creator, this.hasContact);
    }
    this.onCancel();
  }

  onCancel(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  private initFrom(): void {
    let firstname = '';
    let lastname = '';
    let role = 'AUTHOR';
    let postalCode = '';
    let street = '';
    let city = '';
    let country = '';
    let streetNumber = '';
    let phoneNumber = '';
    let website = '';
    let mailAddress = '';

    if (this.editMode) {
      const creator: Creator = this.creatorsService.getCreatorById(this.id);
      firstname = creator.firstName;
      lastname = creator.lastName;
      role = creator.role;

      if (creator.contact) {
        this.hasContact = true;
        postalCode = creator.contact.postalCode;
        street = creator.contact.street;
        city = creator.contact.city;
        country = creator.contact.country;
        streetNumber = creator.contact.streetNumber;
        phoneNumber = creator.contact.phoneNumber;
        website = creator.contact.website;
        mailAddress = creator.contact.mailAddress;
      }

      let firstnamePlaceholder = firstname;
      if (firstname) {
        firstnamePlaceholder = firstname + ' ';
      }
      this.label = 'Édition de l\'auteur \"' + firstnamePlaceholder + lastname + '\"';
    } else {
      this.label = 'Création d\'un auteur';
    }

    this.contactForm = new UntypedFormGroup({
      'postalCode': new UntypedFormControl(postalCode, [Validators.maxLength(50)]),
      'street': new UntypedFormControl(street, [Validators.maxLength(255)]),
      'city': new UntypedFormControl(city, [Validators.maxLength(50)]),
      'country': new UntypedFormControl(country, [Validators.required, Validators.maxLength(50)]),
      'streetNumber': new UntypedFormControl(streetNumber, [Validators.maxLength(10)]),
      'phoneNumber': new UntypedFormControl(phoneNumber, [Validators.maxLength(50)]),
      'website': new UntypedFormControl(website, [Validators.maxLength(75)]),
      'mailAddress': new UntypedFormControl(mailAddress, [Validators.maxLength(320)])
    });

    this.creatorForm = new UntypedFormGroup({
      'firstName': new UntypedFormControl(firstname, [Validators.maxLength(50)]),
      'lastName': new UntypedFormControl(lastname, [Validators.required, Validators.maxLength(50)]),
      'role': new UntypedFormControl(role, [Validators.required, Validators.maxLength(50)]),
    }, (!this.editMode) ? {validators: this.namesExistValidator} : {validators: this.namesExistEditModeValidator});

    if (this.hasContact) {
      this.creatorForm.addControl('contact', this.contactForm);
    }
  }

  private populateRoles(): void {
    this.rolesList = Object.keys(CreatorRoleEnum);
    this.actualEnumType = CreatorRoleEnum;
  }

  namesExistValidator: ValidatorFn = (control: UntypedFormGroup): ValidationErrors | null => {

    const names = this.creatorsService.getExistingNames();
    const currentFirstName: string = control.get('firstName').value.toLowerCase().trim();
    const currentLastName: string = control.get('lastName').value.toLowerCase().trim();

    for (const person of names) {
      if (currentFirstName === person.firstName && currentLastName === person.lastName) {
        return {namesExist: true};
      }
    }
    return null;
  }

  namesExistEditModeValidator: ValidatorFn = (control: UntypedFormGroup): ValidationErrors | null => {

    let names = this.creatorsService.getExistingNames();
    const currentFirstName: string = control.get('firstName').value.toLowerCase().trim();
    const currentLastName: string = control.get('lastName').value.toLowerCase().trim();
    const editedFirstName = this.creatorsService.getCreatorById(this.id).firstName.toLowerCase().trim();
    const editedLastName = this.creatorsService.getCreatorById(this.id).lastName.toLowerCase().trim();

    names = names.filter(person => !(person.firstName === editedFirstName && person.lastName === editedLastName));

    for (const person of names) {
      if (currentFirstName === person.firstName && currentLastName === person.lastName) {
        return {namesExist: true};
      }
    }
    return null;
  }
}
