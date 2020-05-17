import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { TableModule } from 'primeng/components/table/table';
import { DropdownModule } from 'primeng/components/dropdown/dropdown';
import { InputTextModule } from 'primeng/components/inputtext/inputtext';
import { ButtonModule } from 'primeng/components/button/button';
import { CalendarModule } from 'primeng/components/calendar/calendar';
import { TooltipModule } from 'primeng/components/tooltip/tooltip';
import { ChipsModule } from 'primeng/components/chips/chips';
import { DialogModule } from 'primeng/components/dialog/dialog';

import { LoadingModule } from 'ngx-loading';

import { ElectionFormComponent } from './election-form/election-form.component';
import { ElectionSearchComponent } from './election-search/election-search.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    ElectionSearchComponent,
    ElectionFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,

    InputTextModule,
    ButtonModule,
    TableModule,
    DropdownModule,
    CalendarModule,
    TooltipModule,
    ChipsModule,
    DialogModule,

    LoadingModule,

    SharedModule
  ]
})
export class ElectionModule {
}
