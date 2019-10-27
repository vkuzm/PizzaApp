export class CartItem {

  productId: number;
  name: string;
  shortDescription: string;
  price: number;
  total: number;
  image: string;
  quantity: number;

  constructor(productId: number, quantity: number) {
    this.productId = productId;
    this.quantity = quantity;
  }

}
