import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cart } from '../Cart';
import { User } from '../User';
import { CartService } from '../cart.service';
import { CustomerService } from '../customer.service';
import { LoginService } from '../login.service';
import { Products } from '../Products';

@Component({
  selector: 'app-customer-page',
  templateUrl: './customer-page.component.html',
  styleUrls: ['./customer-page.component.css']
})
export class CustomerPageComponent implements OnInit {
  products: Products[] = [];
  p!: Products;
  c!: Cart;
  cartId!: number;
  q!: number;
  cart!: Cart;
  constructor(private cs: CustomerService, private router: Router, private loginService: LoginService, private cartService: CartService) { }

  ngOnInit(): void {

    this.loginService.checkLoginStatus().subscribe({
      next: (res) => {
        if (res.status === 200) {
          let body = <User> res.body;

          if (body.role.role === 'customer') {
            console.log(body);
            this.cartId=body.id;
            console.log(this.cartId);
          }
        }
      },
      error: (err) => {
        if(err.status === 400){
          this.router.navigate(['']);
        }
      }
    })


    this.cs.getAllProducts().subscribe((res) =>{
      if(res.status === 200) {
        let body = <Products[]> res.body;
        console.log(body);
        this.products = body;
        console.log(this.products);
      }
    })
  }

  onButtonClick() { // clicking logout button
    console.log('testing');

    this.loginService.logout().subscribe((res) => {
      console.log(res);

      if (res.status === 200) {
        this.router.navigate(['/']);
      }
    }, (err) => {

    });
  }
}
