import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./layouts/home/home.component";
import {BlogComponent} from "./layouts/blog/blog.component";
import {BlogSingleComponent} from "./layouts/blog-single/blog-single.component";
import {MenuComponent} from "./layouts/menu/menu.component";
import {ServicesComponent} from "./layouts/services/services.component";
import {ContactsComponent} from "./layouts/contacts/contacts.component";
import {CheckoutComponent} from "./layouts/checkout/checkout.component";
import {OrderSuccessComponent} from "./layouts/order-success/order-success.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    data: {
      title: 'Home',
      description: ''
    }
  },
  {
    path: 'menu',
    component: MenuComponent,
    data: {
      title: 'Menu',
      description: ''
    }
  },
  {
    path: 'services',
    component: ServicesComponent,
    data: {
      title: 'Services',
      description: ''
    }
  },
  {
    path: 'contacts',
    component: ContactsComponent,
    data: {
      title: 'Contacts',
      description: ''
    }
  },
  {
    path: 'blog',
    component: BlogComponent,
    data: {
      title: 'Blog',
      description: ''
    }
  },
  {
    path: 'blog/:id',
    component: BlogSingleComponent,
    data: {
      title: '',
      description: ''
    }
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    data: {
      title: 'Checkout',
      description: ''
    }
  },
  {
    path: 'orderSuccess',
    component: OrderSuccessComponent,
    data: {
      title: 'Order is success',
      description: ''
    }
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
