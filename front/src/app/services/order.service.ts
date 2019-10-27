import {Injectable} from '@angular/core';
import {ApiUrls} from "../apiUrls";
import {Order} from "../models/order";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  constructor(private http: HttpClient, private apiUrl: ApiUrls) {
  }

  public async sendOrder(order: Order): Promise<Order> {
    const httpOptions: { headers } = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }),
    };

    try {
      let response = await this.http
        .post(this.apiUrl.saveOrderUrl, order, httpOptions)
        .toPromise();

      return response as Order;

    } catch (error) {
      await console.log(error);
    }
  }

}
