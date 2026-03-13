import { Component, OnInit } from '@angular/core';
import { FormationService } from '../../../infrastructure/services/formation.service';
import { Formation } from '../../../core/models/formation.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardFormateurComponent implements OnInit {
  formations: Formation[] = [];

  constructor(private formationService: FormationService) { }

  ngOnInit(): void {
   
  }

  mockDownload() {
    alert('Generating report... Your download will start shortly.');
  }
}
