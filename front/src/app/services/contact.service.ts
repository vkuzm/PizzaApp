import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";
import {Email} from "../models/email";

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(
    private http: HttpClient,
    private apiUrl: ApiUrls) {
  }

  public sendEmail(email: Email) {
    const httpOptions: { headers; observe; } = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }),
      observe: 'response'
    };

    return this.http.post(this.apiUrl.contactsUrl, email, httpOptions);
  }

}
