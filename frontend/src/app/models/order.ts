import {OrderProduct} from "./order-product";

export class Order {
  orderId: number;
  firstName: string;
  lastName: string;
  email: string;
  city: string;
  comment: string;
  subtotal: number;
  total: number;
  shippingMethod: string;
  paymentMethod: string;
  orderProducts: OrderProduct[] = [];
}

