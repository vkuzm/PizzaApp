import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";
import {Observable} from "rxjs";
import {AppUrls} from "../appUrls";
import {Page} from "../models/page";

@Injectable({
  providedIn: 'root'
})
export class PageService {

  constructor(private http: HttpClient, private apiUrl: ApiUrls, private appUrls: AppUrls) {
  }

  public findById(pageId: number): Observable<Page> {
    return this.http.get<Page>(this.apiUrl.pageUrl + "/" + pageId);
  }

}
