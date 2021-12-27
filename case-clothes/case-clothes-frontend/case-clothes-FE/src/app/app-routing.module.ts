import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { CustomerPageComponent } from './customer-page/customer-page.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { CartComponent } from './cart/cart.component';
import { AddProductComponent } from './add-product/add-product.component';

const routes: Routes = [
  { path: '', component: LoginPageComponent },
  { path: 'customer', component: CustomerPageComponent },
  { path: 'admin', component: AdminPageComponent },
  { path: 'cart', component: CartComponent },
  { path: 'add-product', component: AddProductComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
