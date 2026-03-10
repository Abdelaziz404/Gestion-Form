import { Component, OnInit } from '@angular/core';
import { FormationService } from '../../../infrastructure/services/formation.service';
import { Formation } from '../../../core/models/formation.model';
import { MOCK_FORMATEUR } from '../../../infrastructure/mock/mock-data';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardFormateurComponent implements OnInit {
  user = MOCK_FORMATEUR;
  formations: Formation[] = [];

  constructor(private formationService: FormationService) { }

  ngOnInit(): void {
    this.formationService.getAssignedFormations(MOCK_FORMATEUR.personId)
      .subscribe(data => this.formations = data);
  }

  mockDownload() {
    alert('Generating report... Your download will start shortly.');
  }
}
