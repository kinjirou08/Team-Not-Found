import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  login(uName: string, pass: string) {
    return this.http.post('http://localhost:8082/login', {
      "username": uName,
      "password": pass
    }, {
      withCredentials: true,
      observe: 'response' // gives us an HttpResponse object inside of the observable instead of the response body directly
    })
  }

  checkLoginStatus() {
    return this.http.get('http://localhost:8082/loginstatus', {
      observe: 'response',
      withCredentials: true
    })
  }

  signup(username: string, password: string, firstName: string, lastName: string,
    email: string, phoneNumber:string, address: string) {
      return this.http.post('http://localhost:8082/users',{
        "username": username,
        "password": password,
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "phoneNumber": phoneNumber,
        "address": address
      }, {
        withCredentials:true,
        observe:'response'
      })

  }

  logout() {
    return this.http.post('http://ec2-34-211-207-79.us-west-2.compute.amazonaws.com:8082/logout', {}, {
      observe: 'response',
      withCredentials: true,
      responseType: 'text'
    })
  }

}
