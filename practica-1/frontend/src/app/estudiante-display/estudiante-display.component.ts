import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card'
import {Estudiante} from "../estudiante";

@Component({
  selector: 'app-estudiante-display',
  templateUrl: './estudiante-display.component.html',
  styleUrls: ['./estudiante-display.component.css'],
  standalone: true,
  imports: [MatButtonModule, MatCardModule]
})
export class EstudianteDisplayComponent {
  @Input() estudiante: Estudiante = new Estudiante(0, "", "", "");
  @Output() editItemEvent = new EventEmitter();
  @Output() removeItemEvent = new EventEmitter();
}
