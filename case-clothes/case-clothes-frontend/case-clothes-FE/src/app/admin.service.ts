import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor( private http: HttpClient) { }

  getAllProducts() {
    return this.http.get('http://localhost:8080/products', {
      withCredentials: true,
      observe: 'response',
      
    });
  }

}
