import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CountryDataService} from '../../../shared/services/country-data.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Publisher} from '../../../model/publisher.model';
import {PublisherDataService} from '../publisher-data.service';
import {PublisherService} from '../publisher.service';

@Component({
  selector: 'app-publisher-edit',
  templateUrl: './publisher-edit.component.html',
  styleUrls: ['./publisher-edit.component.css']
})
export class PublisherEditComponent implements OnInit {
  editMode: boolean;
  hasContact: boolean;
  private subscription: Subscription;
  private id: number;
  publisherForm: FormGroup;
  contactForm: FormGroup;
  label: string;
  existingNames: string[];

  constructor(private publishersService: PublisherService,
              private publishersDataService: PublisherDataService,
              private countryDataService: CountryDataService,
              private route: ActivatedRoute,
              private router: Router) {
  }


  ngOnInit(): void {
    this.hasContact = false;
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
    this.publisherForm.addControl('contact', this.contactForm);
  }

  onRemoveContactForm(): void {
    this.hasContact = false;
    this.publisherForm.removeControl('contact');
  }

  onSubmit(): void {
    const publisher = this.publisherForm.value;

    if (this.editMode) {
      const storedPublisher = this.publishersService.getPublisherById(this.id);
      if (storedPublisher
        && storedPublisher.contact) {
        publisher.contact.id = storedPublisher.contact.id;
      }

      this.publishersDataService.editPublisher(this.id, publisher);
    } else {
      this.publishersDataService.addPublisher(publisher, this.hasContact);
    }
    this.onCancel();
  }

  onCancel(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }

  private initFrom(): void {
    let name = '';
    let postalCode = '';
    let street = '';
    let city = '';
    let country = '';
    let streetNumber = '';
    let phoneNumber = '';
    let website = '';
    let mailAddress = '';

    if (this.editMode) {
      const publisher: Publisher = this.publishersService.getPublisherById(this.id);
      name = publisher.name;

      if (publisher.contact) {
        this.hasContact = true;
        postalCode = publisher.contact.postalCode;
        street = publisher.contact.street;
        city = publisher.contact.city;
        country = publisher.contact.country;
        streetNumber = publisher.contact.streetNumber;
        phoneNumber = publisher.contact.phoneNumber;
        website = publisher.contact.website;
        mailAddress = publisher.contact.mailAddress;
      }

      this.label = 'Modification de l\'éditeur \"' + name + '\"';
    } else {
      this.label = 'Création d\'un éditeur';
    }

    this.contactForm = new FormGroup({
      'postalCode': new FormControl(postalCode, [Validators.maxLength(50)]),
      'street': new FormControl(street, [Validators.maxLength(255)]),
      'city': new FormControl(city, [Validators.maxLength(50)]),
      'country': new FormControl(country, [Validators.required, Validators.maxLength(50)]),
      'streetNumber': new FormControl(streetNumber, [Validators.maxLength(10)]),
      'phoneNumber': new FormControl(phoneNumber, [Validators.maxLength(50)]),
      'website': new FormControl(website, [Validators.maxLength(75)]),
      'mailAddress': new FormControl(mailAddress, [Validators.maxLength(320)])
    });

    this.publisherForm = new FormGroup({
      'name': new FormControl(name, [Validators.required, Validators.maxLength(255), this.nameAlreadyExists.bind(this)])
    });

    if (this.hasContact) {
      this.publisherForm.addControl('contact', this.contactForm);
    }
  }

  nameAlreadyExists(control: FormControl): { [s: string]: boolean } {
    /* We need spit the case edit mode or not to allow save the current edited name */
    if ((
        !this.editMode
        &&
        this.publishersService.getExistingNames().indexOf(control.value.toLowerCase().trim()) !== -1
      )
      || (
        this.editMode
        &&
        control.value.toLowerCase().trim() !== this.publishersService.getPublisherById(this.id).name.toLowerCase().trim()
        &&
        this.publishersService.getExistingNames().indexOf(control.value.toLowerCase().trim()) !== -1
      )
    ) {
      return {'nameAlreadyExists': true};
    }
    return null;
  }
}
