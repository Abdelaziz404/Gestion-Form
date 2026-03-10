import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormationService } from '../../../infrastructure/services/formation.service';
import { Formation } from '../../../core/models/formation.model';
import { Seance } from '../../../core/models/seance.model';

@Component({
  selector: 'app-formation-detail',
  templateUrl: './formation-detail.component.html',
  styleUrls: ['./formation-detail.component.scss']
})
export class FormationDetailComponent implements OnInit {
  formation?: Formation;
  seances: Seance[] = [];

  showAddSession = false;
  newSeance: Seance = {
    seanceId: 0,
    formationId: 0,
    date: '',
    heureDebut: '',
    salle: ''
  };

  constructor(
    private route: ActivatedRoute,
    private formationService: FormationService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.newSeance.formationId = id;
    this.loadData(id);
  }

  loadData(id: number) {
    this.formationService.getFormationDetails(id).subscribe(data => this.formation = data);
    this.formationService.getSeancesByFormation(id).subscribe(data => this.seances = data);
  }

  toggleAddSession() {
    this.showAddSession = !this.showAddSession;
  }

  addSeance() {
    if (this.newSeance.date && this.newSeance.heureDebut) {
      const seanceToCreate = { ...this.newSeance, seanceId: Math.random() };
      this.formationService.createSeance(seanceToCreate).subscribe(() => {
        this.loadData(this.newSeance.formationId);
        this.showAddSession = false;
        this.newSeance.date = '';
        this.newSeance.heureDebut = '';
        this.newSeance.salle = '';
        alert('Session added successfully!');
      });
    }
  }

  mockDownload() {
    alert('Generating report... Your download will start shortly.');
  }

  closeFormation() {
    // Logic for closing formation and triggering attestation check (>= 70%)
    alert('Formation closing initiated. Attestations will be generated for eligible participants (>= 70% attendance).');
  }
}
