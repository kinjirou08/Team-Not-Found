import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { LoginService } from '../login.service';
import { User } from '../User';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  username!: string;
  password!: string;


  userName!: string;
  passWord!: string;
  firstName!: string;
  lastName!: string;
  email!: string;
  pNumber! : string;
  address!: string;

  errorMessage!: string;

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit(): void {

    // invoke check if logged in
    this.checkIfLoggedIn();

  }

  checkIfLoggedIn() {
    this.loginService.checkLoginStatus().subscribe({
    next: (res) => {
        if (res.status === 200) {
          let body = <User> res.body;

          if (body.role.role === 'customer') {
            this.router.navigate(['customer']);
          }

          if (body.role.role === 'admin') {
            this.router.navigate(['admin']);
          }
        }
      },
    error: (err) => {
      console.log(err);
    }
    });
  }

  onButtonClick() {
    this.loginService.login(this.username, this.password).subscribe(
      {
        next: (res) => {
          if (res.status === 200) {
            let body = <User> res.body;

            if (body.role.role === 'customer') {
              this.router.navigate(['customer']);
            }

            if (body.role.role === 'admin') {
              this.router.navigate(['admin']);
            }
          }
        },
        error: (err) => {
          this.errorMessage = err.error;
        }
      }
    );
  }

  onSubmitButtonClick(){
    this.loginService.signup(this.userName, this.passWord, this.firstName, this.lastName,
      this.email, this.pNumber, this.address).subscribe({
        next: (res) => {
          let body = <User> res.body;

          console.log(body);
        }
      })
  }

}