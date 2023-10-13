import {Component} from '@angular/core';
import {NAV, LayoutService} from './shared/services/layout.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'grove-webapp';

  constructor(private layoutService: LayoutService) {
    this.layoutService.mode = NAV;
    this.layoutService.entity = null;
  }

  isNavigation(): boolean {
    return this.layoutService.mode === NAV;
  }
}
