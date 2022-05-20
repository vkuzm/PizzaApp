import {Component, OnInit} from '@angular/core';
import {MetaService} from "../../services/meta.service";
import {CustomFieldService} from "../../services/customField-service";
import {BlogService} from "../../services/blog.service";
import {GetResponseBlog} from "../../responses/getResponseBlog";
import {Blog} from "../../models/blog";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public customFields = new Map();
  private recentPosts: Blog[];
  private limitRecentPosts = 2;

  constructor(
    private meta: MetaService,
    private customFieldService: CustomFieldService,
    private blogService: BlogService) {

    this.meta.updateTitle();
  }

  private loadCustomFields() {
    this.customFieldService.getCustomFields()
      .subscribe(fields => {
        fields.forEach(field => {
            this.customFields.set(field.fieldName, field.content);
          }
        )
      })
  }

  ngOnInit() {
    this.initializeScripts();
    this.loadCustomFields();
    this.getRecentPosts();
  }

  private getRecentPosts() {
    this.blogService.getLatest(this.limitRecentPosts)
      .subscribe((response: GetResponseBlog) => {
        this.recentPosts = response._embedded.posts;

        this.blogService.generatePostLinks(this.recentPosts);
      })
  }

  private initializeScripts() {
    AppComponent.loadScript("https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false");
    AppComponent.loadScript("assets/js/google-map.js");
    AppComponent.loadScript("assets/js/main.js");
  }

  private static loadScript(url: string) {
    const body = <HTMLDivElement>document.body;
    const script = document.createElement('script');
    script.innerHTML = '';
    script.src = url;
    script.async = false;
    script.defer = true;
    body.appendChild(script);
  }
}
