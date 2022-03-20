import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './layouts/app/app.component';
import {HomeComponent} from './layouts/home/home.component';
import {SliderComponent} from './components/home-slider/slider.component';
import {TopProductsComponent} from './components/home-top-products/top-products.component';
import {GalleryComponent} from './components/home-gallery/gallery.component';
import {ShowcaseComponent} from './components/home-showcase/showcase.component';
import {RecentBlogComponent} from './components/home-recent-blog/recent-blog.component';
import {ContactFormComponent} from './components/home-contact-form/contact-form.component';
import {ComponentService} from "./services/component.service";
import {ProductService} from "./services/product.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ApiUrls} from "./apiUrls";
import {BlogComponent} from './layouts/blog/blog.component';
import {AppUrls} from "./appUrls";
import {BlogSingleComponent} from './layouts/blog-single/blog-single.component';
import {MenuComponent} from './layouts/menu/menu.component';
import {ServicesComponent} from './layouts/services/services.component';
import {ContactsComponent} from './layouts/contacts/contacts.component';
import {CheckoutComponent} from './layouts/checkout/checkout.component';
import {MiniCartComponent} from './components/mini-cart/mini-cart.component';

import {CookieService} from 'ngx-cookie-service';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import {FieldErrorDisplayComponent} from "./components/field-error-display/field-error-display.component";
import { OrderSuccessComponent } from './layouts/order-success/order-success.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SliderComponent,
    TopProductsComponent,
    GalleryComponent,
    ShowcaseComponent,
    RecentBlogComponent,
    ContactFormComponent,
    BlogComponent,
    BlogSingleComponent,
    MenuComponent,
    ServicesComponent,
    ContactsComponent,
    CheckoutComponent,
    MiniCartComponent,
    SafeHtmlPipe,
    FieldErrorDisplayComponent,
    OrderSuccessComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [ApiUrls, AppUrls, ComponentService, ProductService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule {

}
