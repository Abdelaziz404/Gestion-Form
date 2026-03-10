import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormationService } from '../../../infrastructure/services/formation.service';
import { Formation } from '../../../core/models/formation.model';
import { Seance } from '../../../core/models/seance.model';
import { MOCK_FORMATEUR } from '../../../infrastructure/mock/mock-data';

@Component({
  selector: 'app-formation-create',
  templateUrl: './formation-create.component.html',
  styleUrls: ['./formation-create.component.scss']
})
export class FormationCreateComponent implements OnInit {
  formation: Formation = {
    formationId: Math.floor(Math.random() * 1000) + 200,
    titre: '',
    description: '',
    prix: 0,
    duree: 0,
    formateur: MOCK_FORMATEUR
  };

  constructor(
    private formationService: FormationService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  saveFormation() {
    if (this.formation.titre) {
      this.formationService.createFormation(this.formation).subscribe(() => {
        alert('Formation created successfully! You can now add sessions in the detail view.');
        this.router.navigate(['/formation', this.formation.formationId]);
      });
    } else {
      alert('Please provide a title for the formation.');
    }
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }
}
