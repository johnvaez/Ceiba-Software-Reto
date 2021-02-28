import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2'

//Importamos el servicios
import {  PersonsService } from '../../services/persons.service';

@Component({
  selector: 'app-form-persons',
  templateUrl: './form-persons.component.html',
  styleUrls: ['./form-persons.component.css'],
  providers: [ PersonsService ]
})
export class FormPersonsComponent implements OnInit {

  constructor(private router: Router,
    private formBuilder: FormBuilder, 
    private personsService: PersonsService) { }

  register: FormGroup;
  submitted = false;

  ngOnInit(): void {

    this.register = this.formBuilder.group({
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      idCard: ['', Validators.required],
      dateBirth: ['', Validators.required]
     });
  }

  get validate() { return this.register.controls; }

  //Obtenemos los datos del formulario con form?: NgForm
  onSubmit(form?: NgForm) {

    this.submitted = true;

     //Validación para verificar los campos requeridos
     if (this.register.invalid) {
      return;
     }

    this.personsService.createPerson(form.value)
    .subscribe(res => {

       if(res === 'OK') {
         this.router.navigate(['/personsList']);
         Swal.fire('Registro exitoso','Persona registrada','success')
       }

    });

  }

  validateIdCard(event: any) {
   
    this.personsService.validateIdCard(event.target.value)
    .subscribe(res => {
        if(res['status']){

          this.register = this.formBuilder.group({
            idCard: ['', Validators.required],
           });
           
          Swal.fire('Error',res['message'],'error')
        }
    });
  }

  validateAge(event: any) {

    var yearBirth = event.target.value.split("-");
    var dateNow = new Date();
   
    if(dateNow.getFullYear() - yearBirth[0] < 18) {

       this.register = this.formBuilder.group({
        dateBirth: ['', Validators.required],
       });

      Swal.fire('Error','Menores de edad no se pueden registrar','error')
    }

  }

}
