import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './features/formateur/login/login.component';
import { DashboardFormateurComponent } from './features/formateur/dashboard_formateur/dashboard.component';
import { FormationDetailComponent } from './features/formateur/formation-detail/formation-detail.component';
import { AttendanceComponent } from './features/formateur/attendance/attendance.component';
import { SidebarComponent } from './shared/components/sidebar/sidebar.component';
import { ProfileComponent } from './features/formateur/profile/profile.component';
import { FormationCreateComponent } from './features/formateur/formation-create/formation-create.component';
import { WelcomeComponent } from './features/formateur/welcome/welcome.component';
import { DashboardAdminComponent } from './features/admin/dashboard-admin/dashboard-admin.component';
import { CreateAdminComponent } from './features/admin/create-admin/create-admin.component';
import { NavBarComponent } from './shared/components/vistieur_navbar/nav-bar.component';
import { EnvoyerCandidatureComponent } from './shared/components/envoyer-candidature/envoyer-candidature.component';
import { RegisterComponent } from './shared/components/register/register.component';
import { FormateurSidebarComponent } from './features/formateur/formateur-sidebar/formateur-sidebar.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardFormateurComponent,
    FormationDetailComponent,
    AttendanceComponent,
    SidebarComponent,
    ProfileComponent,
    FormationCreateComponent,
    WelcomeComponent,
    RegisterComponent,
    DashboardAdminComponent,
    CreateAdminComponent,
    NavBarComponent,
    EnvoyerCandidatureComponent,
    FormateurSidebarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
