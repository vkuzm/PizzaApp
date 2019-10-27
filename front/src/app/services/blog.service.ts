import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiUrls} from "../apiUrls";
import {Observable} from "rxjs";
import {GetResponseBlog} from "../responses/getResponseBlog";
import {Blog} from "../models/blog";
import {AppUrls} from "../appUrls";

@Injectable({
  providedIn: 'root'
})
export class BlogService {

  constructor(private http: HttpClient, private apiUrl: ApiUrls, private appUrls: AppUrls) {
  }

  public findAll(page: number): Observable<GetResponseBlog> {
    return this.http.get<GetResponseBlog>(this.apiUrl.blogUrl + "?page=" + page);
  }

  public findById(postId: number): Observable<Blog> {
    return this.http.get<Blog>(this.apiUrl.blogUrl + "/" + postId);
  }

  public getLatest(limit: number): Observable<GetResponseBlog> {
    return this.http.get<GetResponseBlog>(this.apiUrl.componentLatestBlog + "?limit=" + limit);
  }

  public generatePostLinks(posts: Blog[]) {
    return posts.map(p => {
      this.generatePostLink(p);
    });
  }

  public generatePostLink(post: Blog) {
    return post.href = this.appUrls.generatePostUrl(post.postId);
  }

}
