import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/formateur/login/login.component';
import { DashboardFormateurComponent } from './features/formateur/dashboard_formateur/dashboard.component';
import { WelcomeComponent } from './features/formateur/welcome/welcome.component';
import { DashboardAdminComponent } from './features/admin/dashboard-admin/dashboard-admin.component';
import { FormationCreateComponent } from './features/formateur/formation-create/formation-create.component';
import { FormationDetailComponent } from './features/formateur/formation-detail/formation-detail.component';
import { AttendanceComponent } from './features/formateur/attendance/attendance.component';
import { ProfileComponent } from './features/formateur/profile/profile.component';
import { EnvoyerCandidatureComponent } from './features/formateur/envoyer-candidature/envoyer-candidature.component';
import { RegisterComponent } from './shared/components/register/register.component';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard_formateur', component: DashboardFormateurComponent, canActivate: [AuthGuard] },
  { path: 'admin-dashboard', component: DashboardAdminComponent, canActivate: [AuthGuard] },

  { path: 'formation/create', component: FormationCreateComponent, canActivate: [AuthGuard] },
  { path: 'formation/:id', component: FormationDetailComponent, canActivate: [AuthGuard] },
  { path: 'attendance/:seanceId', component: AttendanceComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'envoyer-candidature', component: EnvoyerCandidatureComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
