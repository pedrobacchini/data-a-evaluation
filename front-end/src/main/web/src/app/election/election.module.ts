import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ElectionSearchComponent } from './election-search/election-search.component';
import { ElectionFormComponent } from './election-form/election-form.component';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/components/dropdown/dropdown';
import { InputTextModule } from 'primeng/components/inputtext/inputtext';
import { ButtonModule } from 'primeng/components/button/button';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/components/calendar/calendar';


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
  ]
})
export class ElectionModule {
}
