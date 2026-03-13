import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormationService } from '../../../infrastructure/services/formation.service';
import { Formation } from '../../../core/models/formation.model';

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
  };

  constructor(
    private formationService: FormationService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  saveFormation() {
    if (this.formation.titre) {
   
  }}
}
