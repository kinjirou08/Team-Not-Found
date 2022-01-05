import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cart } from '../Cart';
import { CartService } from '../cart.service';
import { LoginService } from '../login.service';
import { Products } from '../Products';
import { User } from '../User';

@Component({
  selector: 'app-product-display',
  templateUrl: './product-display.component.html',
  styleUrls: ['./product-display.component.css']
})
export class ProductDisplayComponent implements OnInit {

 @Input()
 product!: Products;
 cartId!: number;
 q!: number;
  constructor(private cartService: CartService, private loginService:LoginService, private router: Router) { }

  ngOnInit(): void {
    this.loginService.checkLoginStatus().subscribe({
      next: (res) => {
        if (res.status === 200) {
          let body = <User> res.body;

          if (body.role.role === 'customer') {
            // console.log(body);
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


  }

  onBuyButtonClick(productId: number){
    this.cartService.addToCart(String(productId), String("1"), String(this.cartId)).subscribe({
      next: (res) =>{
        if(res.status === 200) {
          let body = <Cart> res.body;
          console.log(body);
        }
      },
      error: (err) =>{
        console.log(err);

      }
    })

  }

}
