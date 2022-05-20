import {Component, OnInit} from '@angular/core';
import {BlogService} from "../../services/blog.service";
import {GetResponseBlog} from "../../responses/getResponseBlog";
import {Blog} from "../../models/blog";
import {AppUrls} from "../../appUrls";

@Component({
  selector: 'app-component-recent-blog',
  templateUrl: './recent-blog.component.html',
  styleUrls: ['./recent-blog.component.css']
})
export class RecentBlogComponent implements OnInit {
  limit: number = 3;
  posts: Blog[];

  constructor(private appUrls: AppUrls, private blogService: BlogService) {
  }

  ngOnInit() {

    this.blogService.getLatest(this.limit)
      .subscribe((response: GetResponseBlog) => {
        this.posts = response._embedded.posts;

        this.blogService.generatePostLinks(this.posts);
      })
  }

}
