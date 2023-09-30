import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Estudiante} from "../estudiante";
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-estudiante-input',
  templateUrl: './estudiante-input.component.html',
  styleUrls: ['./estudiante-input.component.css'],
  standalone: true,
  imports: [MatInputModule, MatFormFieldModule, FormsModule, MatIconModule, MatDividerModule, MatButtonModule]
})
export class EstudianteInputComponent {
  @ViewChild("estudianteForm") estudianteForm!: NgForm;
  @Output() newDataEvent = new EventEmitter();
  constructor(private http: HttpClient) {}


  // Keep the button disabled initially
  isDisabled: boolean = true;

  onUserInput() {
    // Get the input text
    this.isDisabled = this.estudianteForm.controls['matricula'].value == '' || this.estudianteForm.controls['telefono'].value == '' ||
      this.estudianteForm.controls['matricula'].value == null || this.estudianteForm.controls['telefono'].value == null ||
      this.estudianteForm.controls['nombre'].value == '' || this.estudianteForm.controls['apellido'].value == '';
  }

  onSubmit(): void{
    this.http.post<Estudiante>(
      "http://localhost:8080/estudiantes",
      this.estudianteForm.value
    ).subscribe(
      (data) => {
        this.newDataEvent.emit(data)
      },
      (err) => {
        alert(err.error.message);
      });
  }
}
