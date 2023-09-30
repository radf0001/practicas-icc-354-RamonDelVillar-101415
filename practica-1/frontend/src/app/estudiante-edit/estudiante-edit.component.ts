import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {FormsModule, NgForm} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {HttpClient} from "@angular/common/http";
import {Estudiante} from "../estudiante";
import {MatIconModule} from "@angular/material/icon";
import {MatDividerModule} from "@angular/material/divider";

@Component({
  selector: 'app-estudiante-edit',
  templateUrl: './estudiante-edit.component.html',
  styleUrls: ['./estudiante-edit.component.css'],
  imports: [MatInputModule, MatFormFieldModule, FormsModule, MatIconModule, MatDividerModule, MatButtonModule],
  standalone: true
})
export class EstudianteEditComponent {
  @Input() estudiante: Estudiante = new Estudiante(0, "", "", "");
  @Output() editDataEvent = new EventEmitter();
  constructor(private http: HttpClient) {}


  // Keep the button disabled initially
  // isDisabled: boolean = true;

  // onUserInput() {
  //   // Get the input text
  //   this.isDisabled = this.estudianteForm.controls['matricula'].value == '' || this.estudianteForm.controls['telefono'].value == '' ||
  //     this.estudianteForm.controls['matricula'].value == null || this.estudianteForm.controls['telefono'].value == null ||
  //     this.estudianteForm.controls['nombre'].value == '' || this.estudianteForm.controls['apellido'].value == '';
  // }

  onSubmit(): void{
    this.http.put<Estudiante>(
      "http://localhost:8080/estudiantes",
      this.estudiante
    ).subscribe(
      (data) => {
        this.editDataEvent.emit(data)
      },
      (err) => {
        alert(err.error.message);
      });
  }
}
