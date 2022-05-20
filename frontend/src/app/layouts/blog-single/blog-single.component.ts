import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Blog} from "../../models/blog";
import {AppUrls} from "../../appUrls";
import {BlogService} from "../../services/blog.service";
import {GetResponseBlog} from "../../responses/getResponseBlog";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CommentService} from "../../services/comment.service";
import {MetaService} from "../../services/meta.service";

@Component({
  selector: 'app-layout-blog-single',
  templateUrl: './blog-single.component.html',
  styleUrls: ['./blog-single.component.css']
})

export class BlogSingleComponent implements OnInit {
  limitRecentPosts: number = 3;
  postId: number;
  post: Blog;
  recentPosts: Blog[];
  commentForm: FormGroup;
  commentSuccess: boolean;

  constructor(private appUrls: AppUrls,
              private route: ActivatedRoute,
              private blogService: BlogService,
              private commentService: CommentService,
              private formBuilder: FormBuilder,
              private meta: MetaService) {
  }

  private getPost() {
    this.blogService.findById(this.postId)
      .subscribe((post: Blog) => {
        this.post = post;
        this.post.href = this.blogService.generatePostLink(this.post);
        this.updateMetaTitle();
      });
  }

  private getRecentPosts() {
    this.blogService.getLatest(this.limitRecentPosts)
      .subscribe((response: GetResponseBlog) => {
        this.recentPosts = response._embedded.posts;

        this.blogService.generatePostLinks(this.recentPosts);
      })
  }

  private initCommentForm() {
    this.commentForm = this.formBuilder.group({
      name: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      message: [null, Validators.required],
    });
  }

  private getPostIdFromUrl() {
    this.route.params.subscribe(params => {
      this.postId = params["id"];
    });
  }

  private updateMetaTitle() {
    if (this.post != null && this.post.title.length > 0) {
      this.meta.updateTitle(this.post.title);
    }
  }

  onSubmit() {
    if (this.commentForm.valid) {
      if (this.commentService.addComment(this.commentForm.getRawValue(), this.postId)) {
        this.resetAndUpdate();
      }

    } else {
      this.validateAllFormFields(this.commentForm);
    }
  }

  private resetAndUpdate() {
    this.commentForm.reset();
    this.commentSuccess = true;
  }

  private validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);

      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});

      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }

    });
  }

  private isFieldValid(field: string) {
    return !this.commentForm.get(field).valid && this.commentForm.get(field).touched;
  }

  private displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }

  ngOnInit() {
    this.getPostIdFromUrl();

    if (this.postId > 0) {
      this.getPost();
      this.getRecentPosts();
      this.initCommentForm();
    }
  }

}
