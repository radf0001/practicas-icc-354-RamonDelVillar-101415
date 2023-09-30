import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstudianteDisplayComponent } from './estudiante-display.component';

describe('EstudianteDisplayComponent', () => {
  let component: EstudianteDisplayComponent;
  let fixture: ComponentFixture<EstudianteDisplayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstudianteDisplayComponent]
    });
    fixture = TestBed.createComponent(EstudianteDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
