import {Injectable} from '@angular/core';
import {CartItem} from "../models/cart-item";
import {CookieService} from "ngx-cookie-service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cookieCart: string = "cart";
  private quantityByDefault: number = 1;
  private items: CartItem[] = [];
  private cartUpdated = new Subject<CartItem[]>();

  constructor(private http: HttpClient, private apiUrl: ApiUrls) {}

  getCart() {
    if (this.getCartCookie()) {
        return this.http.post(
          this.apiUrl.getCartUrl,
          this.items, {
            headers: new HttpHeaders()
              .set('Content-type', 'application/json')
              .set('Accept', 'application/json'),
          });
    }

    return false;
  }

  addToCart(productId: any) {
    event.preventDefault();

    let productExist = false;

    // Increases a quantity if a product already exists in the cart
    if (this.getCartCookie()) {
      this.items.forEach(function (item) {
        if (item.productId == productId) {
          item.quantity++;
          productExist = true;
        }
      });
    }

    // If a product doesn't exist then add a new product to the cart
    if (!productExist) {
      this.items.push(new CartItem(productId, this.quantityByDefault));
    }

    //console.log(this.items);

    this.updateCart();
  }

  changeCart(productId: number, quantity: number) {
    if (this.getCartCookie()) {
      this.items.map((item) => {
        if (item.productId == productId) {
          item.quantity = quantity;
          this.updateCart();
        }
      });
    }
  }

  removeItem(productId: number) {
    let cartItems: CartItem[] = [];

    if (this.getCartCookie()) {
      this.items.map((item, index) => {
        if (item.productId !== productId) {
          cartItems.push(item);
        }
      });

      if (cartItems.length > 0) {
        this.items = cartItems;
        this.updateCart();
      }

    }
  }

  getCartCookie() {
    let getCartProducts = localStorage.getItem(this.cookieCart);
    if (getCartProducts && getCartProducts.length > 0) {
      this.items = JSON.parse(getCartProducts);
    }

    return this.items.length > 0;
  }

  updateCart() {
  console.log('11111', this.items)
    localStorage.setItem(this.cookieCart, JSON.stringify(this.items));
    this.cartUpdated.next();
  }

  getCartData(): Observable<any> {
    return this.cartUpdated.asObservable();
  }

  clearCart() {
    this.items = [];
    this.updateCart();
    localStorage.removeItem(this.cookieCart);
  }

}
