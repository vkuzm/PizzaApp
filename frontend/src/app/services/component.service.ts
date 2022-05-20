import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";
import {Observable} from "rxjs";
import {Slider} from "../models/slider";
import {GetResponseCategory} from "../responses/getResponseCategory";

@Injectable({
  providedIn: 'root'
})
export class ComponentService {
  constructor(private http: HttpClient, private apiUrl: ApiUrls) {
  }

  public getHeaderSlider(): Observable<Slider[]> {
    return this.http.get<Slider[]>(this.apiUrl.componentHeaderSlider);
  }

  public getShowCase(limit: number): Observable<GetResponseCategory> {
    let limitRequest = (limit > 0 ? "?limit=" + limit : "");

    return this.http.get<GetResponseCategory>(this.apiUrl.componentShowCase + limitRequest);
  }

}
