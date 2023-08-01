import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UntypedFormGroup} from '@angular/forms';

@Component({
  selector: 'app-simple-filter-form',
  templateUrl: './simple-filter-form.component.html',
  styleUrls: ['./simple-filter-form.component.css']
})
export class SimpleFilterFormComponent implements OnInit {
  @Input() filterForm: UntypedFormGroup;
  @Output() clickFilter: EventEmitter<any> = new EventEmitter();
  @Output() clickRefresh: EventEmitter<any> = new EventEmitter();
  @Output() clickDelete: EventEmitter<any> = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  onFilter(): void {
    this.clickFilter.emit();
  }

  onRefreshList(): void {
    this.clickRefresh.emit();
  }

  onDelete(): void {
    this.clickDelete.emit();
  }
}
