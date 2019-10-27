import {Component, OnInit} from '@angular/core';
import {Product} from "../../models/product";
import {ProductService} from "../../services/product.service";
import {GetResponseProduct} from "../../responses/getResponseProduct";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-component-top-products',
  templateUrl: './top-products.component.html',
  styleUrls: ['./top-products.component.css']
})
export class TopProductsComponent implements OnInit {

  products: Product[];

  constructor(
    private productService: ProductService,
    private cartService: CartService) {
  }

  ngOnInit() {
    this.productService.getTopProducts()
      .subscribe((response: GetResponseProduct) => {
        this.products = response._embedded.products;
      });
  }

}
