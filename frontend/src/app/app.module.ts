import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonsComponent } from './persons/persons.component';
import { NavComponent } from './utils/nav/nav.component';
import { FormPersonsComponent } from './persons/form-persons/form-persons.component';
import { ListPersonComponent } from './persons/list-person/list-person.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonsComponent,
    NavComponent,
    FormPersonsComponent,
    ListPersonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
