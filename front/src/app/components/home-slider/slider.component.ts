import {Component, OnInit} from '@angular/core';
import {Slider} from "../../models/slider";
import {ComponentService} from "../../services/component.service";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-component-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.css']
})
export class SliderComponent implements OnInit {

  slides: Slider[];

  constructor(
    private componentService: ComponentService,
    private cartService: CartService) {
  }

  ngOnInit() {
    this.componentService.getHeaderSlider().subscribe(data => {
      this.slides = data;
    });
  }

}
