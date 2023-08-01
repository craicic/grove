import {Component, OnDestroy, OnInit} from '@angular/core';
import {MechanismService} from '../mechanism.service';
import {MechanismDataService} from '../mechanism-data.service';
import {Mechanism} from '../../../model/mechansim.model';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {ConfigurationService} from '../../configuration/configuration.service';
import {Page} from '../../../model/page.model';
import {UntypedFormControl, UntypedFormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-mechanism-list',
  templateUrl: './mechanism-list.component.html',
  styleUrls: ['./mechanism-list.component.css']
})
export class MechanismListComponent implements OnInit, OnDestroy {

  mechanisms: Mechanism[];
  private subscription: Subscription;
  page: number;
  totalElements: number;
  pageSize: number;
  filterForm: UntypedFormGroup;

  constructor(private mechanismsService: MechanismService,
              private mechanismsDataService: MechanismDataService,
              private configurationService: ConfigurationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.fetchMechanisms();
    this.subscription = this.mechanismsService.pagedMechanismsChanged.subscribe((pagedMechanisms: Page<Mechanism>) => {
      this.mechanisms = pagedMechanisms.content.slice();
      this.totalElements = pagedMechanisms.totalElements;
    });
    this.initForm();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onRefreshList(): void {
    this.fetchMechanisms();
    this.router.navigate(['/admin/editor/mechanisms']);
  }

  onPageChange(): void {
    this.fetchMechanisms(this.page);
    this.router.navigate(['/admin/editor/mechanisms']);
  }

  onFilter(): void {
    this.fetchMechanisms(0, this.filterForm.value.keyword);
    this.initForm();
    this.router.navigate(['/admin/editor/mechanisms']);
  }

  onDelete(): void {
    this.initForm();
  }

  private fetchMechanisms(page?: number, keyword?: string): void {
    this.mechanismsDataService.fetchMechanisms(page, keyword).subscribe(() => {
      this.page = this.mechanismsService.pagedMechanisms.pageable.pageNumber + 1;
      this.totalElements = this.mechanismsService.pagedMechanisms.totalElements;
      this.pageSize = this.configurationService.getNumberOfElements();
    });
  }

  private initForm(): void {
    this.filterForm = new UntypedFormGroup({
      'keyword': new UntypedFormControl('', [Validators.required, Validators.maxLength(50)])
    });
  }
}
