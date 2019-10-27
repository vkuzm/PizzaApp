import {Category} from "../models/category";

export interface GetResponseCategory {
  _embedded: {
    categories: Category[];
    _links: { self: { href: string } };
  };
}
