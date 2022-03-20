import {Component, OnInit} from '@angular/core';
import {CartItem} from "../../models/cart-item";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-component-mini-cart',
  templateUrl: './mini-cart.component.html',
  styleUrls: ['./mini-cart.component.css']
})
export class MiniCartComponent implements OnInit {

  private items: CartItem[] = [];

  constructor(private cartService: CartService) {
    this.getCart();
  }

  ngOnInit() {
    this.cartService.getCartData().subscribe(() => {
      this.getCart();
    });
  }

  getCart() {
    let result = this.cartService.getCart();

    if (result) {
      result.subscribe((response: CartItem[]) => {
        this.items = response;
      });
    }
  }

  getCartTotal() {
    let total = 0;
    this.items.forEach(item => {
      total += item.quantity;
    });

    return total;
  }

}
