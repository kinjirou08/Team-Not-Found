import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }


  getAllProducts(){
    return this.http.get('http://localhost:8082/products', {
      withCredentials: true,
      observe: 'response'
    });
  }

  addNewProduct(pName: string, pDescription: string, pPrice: number, pId: number,
    pCategory: string, pImageURL: string, pTotalQuantity: number) {

    return this.http.post('http://localhost:8082/products', {
      "name": pName,
      "description": pDescription,
      "price": pPrice,
      "categories": {
          "categoryId": pId,
          "category": pCategory
      },
      "imageURL": pImageURL,
      "totalQuantity": pTotalQuantity
    }, {
      observe: 'response'
    });
  }
}
