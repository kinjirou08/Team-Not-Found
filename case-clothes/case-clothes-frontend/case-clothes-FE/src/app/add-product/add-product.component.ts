import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Products } from '../Products';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  pName!: string;
  pDescription!: string;
  pPrice!: number;
  pId!: number;
  pCategory!: string;
  pImageURL!: string;
  pTotalQuantity!: number;
  errorMessage!: string;

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
  }
  addProductButton() {
    if (this.pCategory === `Men's Clothing`) {
      this.pId = 1;
      this.errorMessage = '';
    } else if (this.pCategory === `Women's Clothing`) {
      this.pId = 2;
      this.errorMessage = '';
    } else {
      this.errorMessage = "Category is Case Sensitive, choose between Men's Clothing or Women's Clothing"
    }
    this.adminService.addNewProduct(this.pName, this.pDescription, this.pPrice,
      this.pId, this.pCategory, this.pImageURL, this.pTotalQuantity).subscribe({
        next: (res) => {
          if (res.status === 201) {
            let body = <Products> res.body;
            console.log(body);
          }
        }
      });
  }
}
