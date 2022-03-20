import {Blog} from "../models/blog";

export interface GetResponseBlog {
  _embedded: {
    posts: Blog[];
    _links: { self: { href: string } };
  };
  page: {
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
  };
}
