import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Estudiante} from "../estudiante";
import {EstudianteEditComponent} from "../estudiante-edit/estudiante-edit.component";
import {EstudianteDisplayComponent} from "../estudiante-display/estudiante-display.component";
import {CommonModule} from "@angular/common";


@Component({
  selector: 'app-estudiante-wrapper',
  templateUrl: './estudiante-wrapper.component.html',
  styleUrls: ['./estudiante-wrapper.component.css'],
  standalone: true,
  imports: [EstudianteEditComponent, EstudianteDisplayComponent, CommonModule]
})
export class EstudianteWrapperComponent {
  @Input() estudiante: Estudiante = new Estudiante(0, "", "", "");
  @Output() removeItemEvent = new EventEmitter();

  editable: boolean = false;

  handleEditClick(): void {
    this.editable = true;
  }
  handleSaveEdition(estudiante: Estudiante): void{
    this.editable = false;
    this.estudiante = estudiante;
  }
}
