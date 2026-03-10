import { Component, OnInit } from '@angular/core';
import { User } from '../../../core/models/user.model';
import { MOCK_FORMATEUR } from '../../../infrastructure/mock/mock-data';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: User = MOCK_FORMATEUR;
  activeTab: 'personal' | 'security' | 'notifications' = 'personal';
  isEditing = false;

  constructor() { }

  ngOnInit(): void {
  }

  setTab(tab: 'personal' | 'security' | 'notifications') {
    this.activeTab = tab;
    this.isEditing = false;
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  saveProfile() {
    // In a real app, we'd call a service to save the data
    this.isEditing = false;
    alert('Profile updated successfully!');
  }
}
