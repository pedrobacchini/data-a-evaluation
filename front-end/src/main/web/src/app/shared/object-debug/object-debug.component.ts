import { Component, Input, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-object-debug',
  templateUrl: './object-debug.component.html'
})
export class ObjectDebugComponent implements OnInit {

  @Input() object;
  showDebug: boolean;

  constructor() { }

  ngOnInit() {
    this.showDebug = environment.dev;
  }

}
