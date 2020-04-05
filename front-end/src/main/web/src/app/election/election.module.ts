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

import { ElectionFormComponent } from './election-form/election-form.component';
import { ElectionSearchComponent } from './election-search/election-search.component';


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
    ChipsModule
  ]
})
export class ElectionModule {
}
