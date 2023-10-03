import {Component} from '@angular/core';
import {NAV, WrapperService} from './shared/services/wrapper.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'grove-webapp';

  constructor(private wrapperService: WrapperService) {
    this.wrapperService.mode = NAV;
    this.wrapperService.entity = null;
  }

  isNavigation(): boolean {
    return this.wrapperService.mode === NAV;
  }
}
