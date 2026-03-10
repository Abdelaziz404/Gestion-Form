import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormateurSidebarComponent } from './formateur-sidebar.component';

describe('FormateurSidebarComponent', () => {
  let component: FormateurSidebarComponent;
  let fixture: ComponentFixture<FormateurSidebarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormateurSidebarComponent]
    });
    fixture = TestBed.createComponent(FormateurSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
