import { Component, OnInit } from '@angular/core';

import { AdminService } from '../admin.service';
import { Products } from '../Products';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  product: Products[] = [];
  constructor(private as: AdminService) { }

  ngOnInit(): void {
    this.as.getAllProducts().subscribe((res) => {
      if (res.status === 200) {
        let body = <Products[]> res.body;
        this.product = body; 
      }
    })
  }

}
