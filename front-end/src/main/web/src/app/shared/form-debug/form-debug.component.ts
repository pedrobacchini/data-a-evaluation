import { Component, Input, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-form-debug',
  templateUrl: './form-debug.component.html'
})
export class FormDebugComponent implements OnInit {

  @Input() form: FormGroup;
  showDebug: boolean;

  constructor() {
  }

  ngOnInit() {
    this.showDebug = environment.dev;
  }

}
