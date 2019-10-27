import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";
import {Comment} from "../models/comment";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, private apiUrl: ApiUrls) {
  }

  public addComment(comment: Comment, postId: number) {
    const httpOptions: { headers; observe; } = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }),
      observe: 'response'
    };

    this.http
      .post(this.apiUrl.getCommentUrl(postId), comment, httpOptions)
      .subscribe(response => {
          console.log(response);
      });

    return true;
  }

}
