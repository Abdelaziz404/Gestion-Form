import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogoutController } from '../../../controller/logoutController';
import { Role } from '../../utils/role';
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
    // Load user from localStorage or service
    const userId = localStorage.getItem('user_id');
    const email = localStorage.getItem('user_email');
    const role = localStorage.getItem('user_role') as Role;
    if (userId && email && role) {
      this.user = {
        personId: parseInt(userId),
        nom: '', // Assuming not stored, set to empty
        prenom: '',
        email: email,
        telephone: '',
        role: role
      };
    }
  }

  logout() {
    this.logoutController.logout(); // Use your LogoutController
  }
}