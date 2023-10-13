import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {SidebarControlService} from '../../../shared/services/sidebar-control.service';

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrls: ['./dashboard-user.component.css']
})
export class DashboardUserComponent implements OnInit, OnDestroy {

  public innerWidth: any;
  isSideCollapsed: boolean;
  private subscription: Subscription;

  constructor(private sidebarService: SidebarControlService) {

  }

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    this.subscription = this.sidebarService.isCollapseSubject.subscribe(value => this.isSideCollapsed = value);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  @HostListener('window:resize', ['$event'])
  onResize(): void {
    this.innerWidth = window.innerWidth;
  }

  onCollapseNavbar(): void {
    this.sidebarService.collapse();
  }
}
