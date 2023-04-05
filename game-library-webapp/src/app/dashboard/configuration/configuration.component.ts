import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfigurationService} from './configuration.service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {
  configForm: FormGroup;
  numberOfElementsPerPage: number;
  numbers: number[] = [1, 2, 3, 5, 7, 10, 20, 25, 50, 100];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private configurationService: ConfigurationService) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.numberOfElementsPerPage = this.configurationService.getNumberOfElements();

    this.configForm = new FormGroup({
      'numberOfElementsControl': new FormControl(this.numberOfElementsPerPage, [Validators.required])
    });
  }

  onSubmit(): void {
    this.configurationService.setNumberOfElements(this.configForm.value.numberOfElementsControl);
    this.initForm();
  }

  onRestore(): void {
    this.initForm();
  }

  onCancel(): void {
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
