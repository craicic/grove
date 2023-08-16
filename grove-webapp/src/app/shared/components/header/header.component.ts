import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SidebarControlService} from '../../services/sidebar-control.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthenticationService} from '../../../auth/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAdministrator: boolean;
// Step 1:
  // Create a property to track whether the menu is open.
  // Start with the menu collapsed so that it does not
  // appear initially when the page loads on a small screen!
  isMenuCollapsed = true;
  setNavOpened: any;

  constructor(
    private auth: AuthenticationService,
    private sidebarService: SidebarControlService,
    private router: Router,
    private http: HttpClient) {
  }

  ngOnInit(): void {
    this.isAdministrator = true;
  }

  authenticated(): boolean {
    return this.auth.authenticated;
  }

  onLogin(): void {
    this.router.navigate(['/login']);
  }

  onLogout(): void {
    this.http.post('logout', {}).subscribe(() => {
      this.auth.authenticated = false;
      this.router.navigate(['/admin/home']);
    }, () => {});
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
