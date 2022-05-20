import {Component, OnInit} from '@angular/core';
import {Product} from "../../models/product";
import {ProductService} from "../../services/product.service";
import {GetResponseProduct} from "../../responses/getResponseProduct";
import {GetResponseCategory} from "../../responses/getResponseCategory";
import {Category} from "../../models/category";
import {AppUrls} from "../../appUrls";
import {ComponentService} from "../../services/component.service";
import {CartService} from "../../services/cart.service";
import {MetaService} from "../../services/meta.service";

@Component({
  selector: 'app-layout-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  productLimit = 9;
  categories: Category[];
  products: Product[];

  constructor(private appUrls: AppUrls,
              private productService: ProductService,
              private componentService: ComponentService,
              private cartService: CartService,
              private meta: MetaService) {

    this.meta.updateTitle();
  }

  ngOnInit() {

    this.productService.getProducts()
      .subscribe((response: GetResponseProduct) => {
        this.products = response._embedded.products;
      });

    this.componentService.getShowCase(this.productLimit)
      .subscribe((response: GetResponseCategory) => {
        this.categories = response._embedded.categories;

        this.categories.forEach(category => {
          category.products.map(product => {
            product.href = this.appUrls.generateProductUrl(product.productId)
          })
        });
      });
  }

}
