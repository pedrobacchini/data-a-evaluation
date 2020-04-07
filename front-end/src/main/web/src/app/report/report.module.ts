import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TableModule } from 'primeng/components/table/table';
import { TooltipModule } from 'primeng/components/tooltip/tooltip';
import { ButtonModule } from 'primeng/components/button/button';
import { InputTextModule } from 'primeng/components/inputtext/inputtext';

import { ReportComponent } from './report.component';

@NgModule({
  declarations: [
    ReportComponent
  ],
  imports: [
    CommonModule,

    TableModule,
    TooltipModule,
    ButtonModule,
    InputTextModule
  ]
})
export class ReportModule {
}
