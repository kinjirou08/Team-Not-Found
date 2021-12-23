import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { CustomerPageComponent } from './customer-page/customer-page.component';
import { AdminPageComponent } from './admin-page/admin-page.component';

const routes: Routes = [
  { path: '', component: LoginPageComponent },
  { path: 'customer', component: CustomerPageComponent },
  { path: 'admin', component: AdminPageComponent }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
