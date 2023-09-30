import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EstudianteDisplayComponent } from './estudiante-display/estudiante-display.component';
import {HttpClientModule} from "@angular/common/http";
import { EstudianteInputComponent } from './estudiante-input/estudiante-input.component';
import { EstudianteWrapperComponent } from './estudiante-wrapper/estudiante-wrapper.component';
import { EstudianteEditComponent } from './estudiante-edit/estudiante-edit.component';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    EstudianteDisplayComponent,
    EstudianteInputComponent,
    EstudianteWrapperComponent,
    EstudianteEditComponent,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
