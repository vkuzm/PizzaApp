import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient, private apiUrl: ApiUrls) {
  }

}
