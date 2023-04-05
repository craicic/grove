import {Component, OnInit} from '@angular/core';
import {WrapperService} from '../../services/wrapper.service';

@Component({
  selector: 'app-locked-header',
  templateUrl: './locked-header.component.html',
  styleUrls: ['./locked-header.component.css']
})
export class LockedHeaderComponent implements OnInit {
  constructor(private wrapperService: WrapperService) {
  }

  ngOnInit(): void {
  }

  getEntity(): string {
    return this.wrapperService.entity;
  }

  getMode(): string {
    return this.wrapperService.MODE_NAMES[this.wrapperService.mode];
  }
}
