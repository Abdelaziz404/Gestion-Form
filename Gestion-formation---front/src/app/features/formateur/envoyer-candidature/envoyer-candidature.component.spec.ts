import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnvoyerCandidatureComponent } from './envoyer-candidature.component';

describe('EnvoyerCandidatureComponent', () => {
  let component: EnvoyerCandidatureComponent;
  let fixture: ComponentFixture<EnvoyerCandidatureComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnvoyerCandidatureComponent]
    });
    fixture = TestBed.createComponent(EnvoyerCandidatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
