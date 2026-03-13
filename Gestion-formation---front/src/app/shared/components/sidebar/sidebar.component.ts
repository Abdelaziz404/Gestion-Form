import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogoutController } from '../../../controller/logoutController';
import { Role } from '../../utils/Enum/role';
import { AuthService } from '../../../core/services/authService/auth.service';
import { User } from '../../../core/models/user.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  isLoggedIn = true; // Temporary for design preview
  role$: Observable<Role | null>;
  user: User | undefined;
  RoleEnum = Role;

  constructor(
    private router: Router,
    private logoutController: LogoutController,
    private authService: AuthService
  ) {
    this.role$ = this.authService.role$;
  }

  ngOnInit(): void {
  }

  logout() {
    this.logoutController.logout(); // Use your LogoutController
  }
}