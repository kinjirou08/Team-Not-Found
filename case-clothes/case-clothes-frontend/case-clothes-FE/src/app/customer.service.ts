import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CartService } from './cart.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient, private cartService: CartService) { }


  getAllProducts(){
    return this.http.get('http://ec2-34-211-207-79.us-west-2.compute.amazonaws.com:8082/products', {
      withCredentials: true,
      observe: 'response'
    });
  }
}
