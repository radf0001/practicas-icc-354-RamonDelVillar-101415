import { Component } from '@angular/core';

import {Estudiante} from "./estudiante";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  estudiantes: Estudiante[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<Estudiante[]>(
      "http://localhost:8080/estudiantes"
    ).subscribe(data => this.estudiantes = data);
  }

  appendData(newEstudiante: any): void {
    this.estudiantes.push(newEstudiante);
  }

  removeItem(estudianteId: number): void{
    this.http.delete(
      "http://localhost:8080/estudiantes/" + estudianteId
    ).subscribe(data =>
      this.estudiantes = this.estudiantes.filter((estudiante: Estudiante) => estudiante.matricula != estudianteId)
    )
  }
}
