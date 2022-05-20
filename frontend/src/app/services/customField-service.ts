import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";
import {Observable} from "rxjs";
import {CustomField} from "../models/custom-field";

@Injectable({
  providedIn: 'root'
})
export class CustomFieldService {
  constructor(private http: HttpClient, private apiUrl: ApiUrls) {
  }

  public getCustomFields(): Observable<CustomField[]> {
    return this.http.get<CustomField[]>(this.apiUrl.customFields);
  }

}
