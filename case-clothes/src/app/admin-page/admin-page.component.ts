import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from '../admin.service';
import { LoginService } from '../login.service';
import { Products } from '../Products';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  products: Products[] = [];
  constructor(private as: AdminService, private router: Router, private loginService: LoginService) { }

  ngOnInit(): void {

    this.loginService.checkLoginStatus().subscribe({
      error: (err) => {
        if(err.status === 400){
          this.router.navigate(['']);
        }
      }
    })


    this.as.getAllProducts().subscribe((res) =>{
      if(res.status === 200) {
        let body = <Products[]> res.body;

        this.products = body;

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
