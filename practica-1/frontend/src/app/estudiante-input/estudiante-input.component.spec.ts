import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstudianteInputComponent } from './estudiante-input.component';

describe('EstudianteInputComponent', () => {
  let component: EstudianteInputComponent;
  let fixture: ComponentFixture<EstudianteInputComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstudianteInputComponent]
    });
    fixture = TestBed.createComponent(EstudianteInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
