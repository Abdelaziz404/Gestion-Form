import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PresenceService } from '../../../infrastructure/services/presence.service';
import { User } from '../../../core/models/user.model';
import { Presence } from '../../../core/models/presence.model';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.scss']
})
export class AttendanceComponent implements OnInit {
  seanceId!: number;
  participants: User[] = [];
  presences: { [key: number]: Presence } = {};

  constructor(
    private route: ActivatedRoute,
    private presenceService: PresenceService,
    private location: Location
  ) { }

  goBack() {
    this.location.back();
  }

  ngOnInit(): void {
    this.seanceId = Number(this.route.snapshot.paramMap.get('seanceId'));
    this.presenceService.getParticipantsBySeance(this.seanceId).subscribe(data => {
      this.participants = data;
      this.participants.forEach(p => {
        this.presences[p.personId] = {
          presenceId: Math.random(),
          estPresent: true,
          observation: '',
          seanceId: this.seanceId,
          participantId: p.personId
        };
      });
    });
  }

  togglePresence(participantId: number) {
    this.presences[participantId].estPresent = !this.presences[participantId].estPresent;
  }

  getAttendanceRate(participantId: number): number {
    // Mock logic: 101 formation has 2 sessions in mock.
    // We'll just return a mock percentage (60% or 85%) for visual demo
    return participantId === 10 ? 85 : 60;
  }

  saveAttendance() {
    Object.values(this.presences).forEach(p => this.presenceService.recordPresence(p));
    alert('Attendance saved successfully!');
  }
}
