import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {SidebarControlService} from '../shared/services/sidebar-control.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

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
