export class AppUrls {
  public readonly baseUrl: string;
  public readonly productUrl: string;
  public readonly blogUrl: string;

  constructor() {
    this.baseUrl = 'http://localhost:4200/';
    this.productUrl = this.baseUrl + "products";
    this.blogUrl = this.baseUrl + "blog";
  }

  public generatePostUrl(id: number) {
    return this.blogUrl + "/" + id;
  }

  public generateProductUrl(id: number) {
    return this.productUrl + "/" + id;
  }
}
