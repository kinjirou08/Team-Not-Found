import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Cart } from 'src/Cart';
import { Products } from './Products';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }
  sub: Subject <Cart> =  new Subject();
  items: Products[] =[];
  p!: Products;

  addToCart(pId: string, quantity: string, cartId: string){

    console.log("cartService>>>", pId);
    console.log(quantity);
    let parameter = new HttpParams();
    parameter = parameter.append('productId', pId);
    parameter = parameter.append('quantity', quantity);
    return this.http.post(`http://localhost:8080/carts/${cartId}`, {},   
      { 
        "params": parameter,     
        withCredentials: true,
        observe:'response'
      })
  }
  

  getCartFromCustomerPage(cartId: string){
    return this.http.get <Cart>(`http://localhost:8080/carts/${cartId}`, {
      withCredentials: true
    }).subscribe((data)=>{
      this.sub.next(data);
    });
  }

  
    deleteProductFromCart(pId: string, cartId: String){
      return this.http.delete(`http://localhost:8080/carts/${cartId}`,   
        { 
          withCredentials: true,
          observe:'response',
          params: {
            productId: pId
          }
          
        })
      }
  

  
}
