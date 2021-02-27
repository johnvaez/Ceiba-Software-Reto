import { Component, OnInit } from '@angular/core';
//Importamos el servicios
import {  PersonsService } from '../../services/persons.service';

@Component({
  selector: 'app-list-person',
  templateUrl: './list-person.component.html',
  styleUrls: ['./list-person.component.css'],
  providers: [ PersonsService ]
})
export class ListPersonComponent implements OnInit {

  constructor(private personsService: PersonsService) { }

  dataPersons: any[];

  ngOnInit(): void {
    this.getPersons();
  }

  getPersons() {
    this.personsService.getPersons()
    .subscribe(data => {
      this.dataPersons = data;
    });
  }

}
