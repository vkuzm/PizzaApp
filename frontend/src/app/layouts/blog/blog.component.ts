import {Component, OnInit} from '@angular/core';
import {Blog} from "../../models/blog";
import {AppUrls} from "../../appUrls";
import {BlogService} from "../../services/blog.service";
import {GetResponseBlog} from "../../responses/getResponseBlog";
import {Pagination} from "../../models/pagination";
import {MetaService} from "../../services/meta.service";

declare function contentWayPointRefresh(): any;

@Component({
  selector: 'app-layout-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {

  posts: Blog[];
  pagination: Pagination;

  constructor(
    private appUrls: AppUrls,
    private blogService: BlogService,
    private meta: MetaService) {

    this.meta.updateTitle();
  }

  getPosts(page: number): void {
    this.blogService.findAll(page)
      .subscribe((response: GetResponseBlog) => {

        this.posts = response._embedded.posts;
        this.blogService.generatePostLinks(this.posts);

        this.pagination = response.page;
      });
  }

  getPagination() {
    let links = [];
    for (let page = 0; page < this.pagination.totalPages; page++) {

      let link = {
        "number": page,
        "active": page == this.pagination.number,
        "text": page + 1
      };

      links.push(link)
    }

    return links;
  }

  switchPage(page: number) {
    event.preventDefault();

    this.getPosts(page);

    contentWayPointRefresh();
  }

  ngOnInit() {
    this.getPosts(0);
  }

}
