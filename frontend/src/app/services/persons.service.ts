import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PersonsService {

  constructor(private http: HttpClient) { }

  //URL de la API
  URL_API = 'http://127.0.0.1:8080/api/persons';

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  //Crear persona
  createPerson(data): Observable<any> {
    return this.http.post<any>(this.URL_API, JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }  

  //Validar No. Cédula
   validateIdCard(id): Observable<any> {
    return this.http.get<any>(this.URL_API+'/'+id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }  

  //Obtener todos las personas
  getPersons(): Observable<any> {
    return this.http.get<any>(this.URL_API)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  } 

   // Manejo de errores
   errorHandl(error) {
    let errorMessage = '';
    if(error.error instanceof ErrorEvent) {
      // Obtener error del lado del cliente
      errorMessage = error.error.message;
    } else {
      // Obtener error del lado del servidor
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
