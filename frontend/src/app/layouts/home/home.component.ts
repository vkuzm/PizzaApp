import {Component, OnInit} from '@angular/core';
import {AppComponent} from "../app/app.component";

@Component({
  selector: 'app-layout-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private app: AppComponent) {
  }

  ngOnInit() {
  }

}
