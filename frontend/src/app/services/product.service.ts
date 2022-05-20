import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiUrls} from "../apiUrls";
import {GetResponseProduct} from "../responses/getResponseProduct";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient, private apiUrl: ApiUrls) {
  }

  public getProducts(): Observable<GetResponseProduct> {
    return this.http.get<GetResponseProduct>(this.apiUrl.productUrl)
  }

  public getTopProducts(): Observable<GetResponseProduct> {
    return this.http.get<GetResponseProduct>(this.apiUrl.componentTopProducts);
  }

}
