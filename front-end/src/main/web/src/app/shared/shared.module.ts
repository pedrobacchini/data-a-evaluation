import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormDebugComponent } from './form-debug/form-debug.component';
import { ObjectDebugComponent } from './object-debug/object-debug.component';

@NgModule({
  declarations: [
    FormDebugComponent,
    ObjectDebugComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FormDebugComponent,
    ObjectDebugComponent
  ]
})
export class SharedModule {
}
