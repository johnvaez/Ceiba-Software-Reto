import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListPersonComponent } from './persons/list-person/list-person.component';
import { PersonsComponent } from './persons/persons.component';

const routes: Routes = [
  {
    path: '',
    component: PersonsComponent
  },
  {
    path:'personsList',
    component:ListPersonComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
