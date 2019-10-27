import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {CartItem} from "../../models/cart-item";
import {MetaService} from "../../services/meta.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {OrderService} from "../../services/order.service";
import {Order} from "../../models/order";
import {OrderProduct} from "../../models/order-product";
import {Router} from "@angular/router";

@Component({
  selector: 'app-layout-order',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})

export class CheckoutComponent implements OnInit {
  cartProducts: CartItem[];
  isLoading = false;
  orderForm: FormGroup;
  orderSuccess: boolean;
  orderError: boolean;
  disableSubmit = false;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private meta: MetaService,
    private formBuilder: FormBuilder,
    private router: Router) {

    this.meta.updateTitle();
    this.getCart();
  }

  private initOrderForm() {
    this.orderForm = this.formBuilder.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      city: [null, Validators.required],
      shippingMethod: [null, Validators.required],
      paymentMethod: [null, Validators.required],
      comment: [null, null],
    });

  }

  async onSubmit() {
    if (this.disableSubmit) {
      return false;
    }
    this.orderError = false;

    if (this.orderForm.valid) {
      this.disableSubmit = true;

      const order = this.generateOrderData();
      const savedOrder = await this.orderService.sendOrder(order);

      if (savedOrder.orderId > 0) {
        this.actionAfterOrder(savedOrder.orderId);
      } else {
        this.orderError = true;
      }

    } else {
      this.disableSubmit = false;
      this.validateAllFormFields(this.orderForm);
    }
  }

  private generateOrderData() {
    const order = new Order();
    order.firstName = this.orderForm.get('firstName').value;
    order.lastName = this.orderForm.get('lastName').value;
    order.email = this.orderForm.get('email').value;
    order.city = this.orderForm.get('city').value;
    order.shippingMethod = this.orderForm.get('shippingMethod').value;
    order.paymentMethod = this.orderForm.get('paymentMethod').value;
    order.comment = this.orderForm.get('comment').value;
    order.subtotal = this.getCartTotalSum();
    order.total = this.getCartTotalSum();

    this.cartProducts.forEach(cartProduct => {
      const orderProduct = new OrderProduct(
        cartProduct.name,
        cartProduct.quantity,
        cartProduct.price,
        cartProduct.total);

      order.orderProducts.push(orderProduct);
    });

    return order;
  }

  private actionAfterOrder(orderId: number) {
    window.location.href = '/orderSuccess?order_id=' + orderId;
    this.cartService.clearCart();
  }

  private validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);

      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});

      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }

    });

    document.getElementById('shipping-cart').scrollIntoView();
  }

  private isFieldValid(field: string) {
    return !this.orderForm.get(field).valid && this.orderForm.get(field).touched;
  }

  private displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }

  ngOnInit() {
    this.cartService.getCartData().subscribe(() => {
      this.getCart();
    });

    this.initOrderForm();
  }

  getCart() {
    let getProducts = this.cartService.getCart();

    if (getProducts) {
      getProducts.subscribe((response: CartItem[]) => {
        this.cartProducts = response;

        setTimeout(() => {
          this.isLoading = false;
        }, 1000);

      });
    }
  }

  getCartTotal() {
    let total = 0;
    this.cartProducts.forEach(item => {
      total += item.quantity;
    });
    return total;
  }

  getCartTotalSum() {
    let total = 0;

    this.cartProducts.forEach((item: CartItem) => {
      total += item.total;
    });

    return total;
  }

  getCartTotalSumFormat() {
    return Number(this.getCartTotalSum()).toLocaleString('en-GB');
  }

  changeQuantity(type: String, productId: number, quantity: number) {
    event.preventDefault();
    this.isLoading = true;

    if (type === 'increase') {
      this.cartService.changeCart(productId, quantity + 1);
    } else {
      this.cartService.changeCart(productId, quantity - 1);
    }
  }

  removeProduct(productId: number) {
    this.cartService.removeItem(productId);
  }

}
