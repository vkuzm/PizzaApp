import {Component, OnInit} from '@angular/core';
import {PageService} from "../../services/page.service.";
import {Page} from "../../models/page";
import {ActivatedRoute} from "@angular/router";
import {MetaService} from "../../services/meta.service";

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {
  page: Page;
  pageId = 46;

  constructor(
    private route: ActivatedRoute,
    private pageService: PageService,
    private meta: MetaService) {
    this.meta.updateTitle();
  }

  private getPage(pageId) {
    this.pageService.findById(pageId)
      .subscribe((page: Page) => {
        this.page = page;
      });
  }

  ngOnInit() {
    this.getPage(this.pageId);
  }

}
