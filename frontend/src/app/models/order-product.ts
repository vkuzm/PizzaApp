export class OrderProduct {
  productName: string;
  quantity: number;
  price: number;
  total: number;

  constructor(productName: string, quantity: number, price: number, total: number) {
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
    this.total = total;
  }

}
