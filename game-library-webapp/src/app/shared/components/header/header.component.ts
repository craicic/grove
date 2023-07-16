import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SidebarControlService} from '../../services/sidebar-control.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAuthenticated: boolean;
  isAdministrator: boolean;
// Step 1:
  // Create a property to track whether the menu is open.
  // Start with the menu collapsed so that it does not
  // appear initially when the page loads on a small screen!
  isMenuCollapsed = true;
  setNavOpened: any;

  constructor(
    private sidebarService: SidebarControlService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.isAuthenticated = true;
    this.isAdministrator = true;
  }

  onLogin(): void {
  }

  onLogout(): void {
  }

  onOpenGamesNavbar(): void {
    this.router.navigate(['/admin/editor']);
    this.sidebarService.expand();
  }

  onOpenLoansNavbar(): void {
    this.router.navigate(['/admin/loans']);
  }

  onOpenMembersNavbar(): void {
    this.router.navigate(['/admin/members']);
  }
}
