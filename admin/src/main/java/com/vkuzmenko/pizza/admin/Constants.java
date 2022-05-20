package com.vkuzmenko.pizza.admin;

public class Constants {
    public static final String BASE_URL = "admin";
    public static final String BLOG_URL = BASE_URL + "/blog";
    public static final String PRODUCT_URL = BASE_URL + "/products";
    public static final String CATEGORY_URL = BASE_URL + "/categories";
    public static final String ORDER_URL = BASE_URL + "/orders";
    public static final String SLIDER_URL = BASE_URL + "/slider";
    public static final int LIMIT_IN_PAGE = 20;
    public static final String BlOG_SERVICE = "http://gateway-service:8080/information-service/api/";
    public static final String PRODUCT_SERVICE = "http://gateway-service:8080/product-service/api/";
    public static final String COMMON_SERVICE = "http://gateway-service:8080/common-service/api/";
    public static final String ORDER_SERVICE = "http://gateway-service:8080/checkout-service/api/";
}
