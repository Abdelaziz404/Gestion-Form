import { Component, Input } from '@angular/core';
import { User } from 'src/app/core/models/user.model';

@Component({
  selector: 'app-formateur-sidebar',
  templateUrl: './formateur-sidebar.component.html',
  styleUrls: ['./formateur-sidebar.component.scss']
})
export class FormateurSidebarComponent {
  @Input() user: User | undefined;
}