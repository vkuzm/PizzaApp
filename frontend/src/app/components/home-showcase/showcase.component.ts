import {Component, OnInit} from '@angular/core';
import {Category} from "../../models/category";
import {AppUrls} from "../../appUrls";
import {GetResponseCategory} from "../../responses/getResponseCategory";
import {ComponentService} from "../../services/component.service";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-component-showcase',
  templateUrl: './showcase.component.html',
  styleUrls: ['./showcase.component.css']
})
export class ShowcaseComponent implements OnInit {
  showcaseProductLimit = 3;
  categories: Category[];

  constructor(private appUrls: AppUrls,
              private componentService: ComponentService,
              private cartService: CartService) {
  }

  ngOnInit() {
    this.componentService.getShowCase(this.showcaseProductLimit)
      .subscribe((response: GetResponseCategory) => {
        this.categories = response._embedded.categories;

        this.categories.forEach(category => {
          if (category.products) {
            category.products.map(product => {
              product.href = this.appUrls.generateProductUrl(product.productId)
            })
          }
        });
      });
  }

}
