import {Product} from "../models/product";

export interface GetResponseProduct {
  _embedded: {
    products: Product[];
    _links: { self: { href: string } };
  };
}
