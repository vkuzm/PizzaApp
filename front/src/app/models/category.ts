import {Product} from "./product";

export class Category {
  categoryId: number;
  name: string;
  description: string;
  createdAt: string;
  products: Product[];
}
