import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class SidebarControlService {

  isCollapseSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor() {
  }

  expand(): void {
    this.isCollapseSubject.next(false);
  }

  collapse(): void {
    this.isCollapseSubject.next(true);
  }
}
