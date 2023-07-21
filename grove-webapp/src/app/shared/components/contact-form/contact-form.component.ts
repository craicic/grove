import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {ContactService} from './contact.service';
import {CountryDataService} from '../../services/country-data.service';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {
  @Input() contactForm: FormGroup;
  countries;

  constructor(private contactService: ContactService,
              private countryDataService: CountryDataService) {
  }

  ngOnInit(): void {
    if (this.countryDataService.countries.length === 0) {
      this.countryDataService.getList();
    }
    this.countries = this.countryDataService.countries;
  }

}
