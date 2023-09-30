import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstudianteWrapperComponent } from './estudiante-wrapper.component';

describe('EstudianteWrapperComponent', () => {
  let component: EstudianteWrapperComponent;
  let fixture: ComponentFixture<EstudianteWrapperComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstudianteWrapperComponent]
    });
    fixture = TestBed.createComponent(EstudianteWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
