export class ApiUrls {
  public readonly productBaseUrl: string;
  public readonly checkoutBaseUrl: string;
  public readonly emailBaseUrl: string;
  public readonly informationBaseUrl: string;
  public readonly productUrl: string;
  public readonly categoryUrl: string;
  public readonly blogUrl: string;
  public readonly checkoutUrl: string;
  public readonly saveOrderUrl: string;
  public readonly getCartUrl: string;
  public readonly pageUrl: string;
  public readonly commentUrl: string;
  public readonly commonBaseUrl: string;
  public readonly componentHeaderSlider: string;
  public readonly componentTopProducts: string;
  public readonly componentLatestBlog: string;
  public readonly componentShowCase: string;
  public readonly customFields: string;
  public readonly emailUrl: string;
  public readonly contactsUrl: string;

  constructor() {
    this.informationBaseUrl = 'http://localhost:8080/information-service/api';
    this.productBaseUrl = 'http://localhost:8080/product-service/api';
    this.checkoutBaseUrl = 'http://localhost:8080/checkout-service/api';
    this.emailBaseUrl = 'http://localhost:8080/email-service/api';
    this.commonBaseUrl = 'http://localhost:8080/common-service/api';

    this.blogUrl = this.informationBaseUrl + "/blog";
    this.commentUrl = "/comments";
    this.pageUrl = this.informationBaseUrl + "/pages";
    this.componentLatestBlog = this.blogUrl + "/latest";

    this.productUrl = this.productBaseUrl + "/products";
    this.categoryUrl = this.productBaseUrl + "/categories";

    this.checkoutUrl = this.checkoutBaseUrl + "/orders";
    this.getCartUrl = this.checkoutBaseUrl + "/cart";
    this.saveOrderUrl = this.checkoutUrl;

    this.componentTopProducts = this.productUrl + "/top";
    this.componentShowCase = this.categoryUrl + "/showcase";
    this.componentHeaderSlider = this.commonBaseUrl + "/slider";
    this.customFields = this.commonBaseUrl + "/customFields";

    this.emailUrl = this.emailBaseUrl + "/email";
    this.contactsUrl = this.emailBaseUrl + "/contactForm";
  }

  public getCommentUrl(postId: number) {
    return this.blogUrl + "/" + postId + this.commentUrl;
  }

}
