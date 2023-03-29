import {Component, OnInit} from '@angular/core';
import {WrapperService} from '../../services/wrapper.service';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css']
})
export class BannerComponent implements OnInit {

  constructor(private wService: WrapperService) {
  }

  ngOnInit(): void {
  }

  getMode(): string {
    return this.wService.getModeName();
  }
}
