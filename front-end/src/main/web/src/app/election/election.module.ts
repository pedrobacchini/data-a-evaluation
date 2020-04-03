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


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,

    InputTextModule,
    ButtonModule,
    TableModule,
    DropdownModule
  ],
  exports: [
    ElectionSearchComponent,
    ElectionFormComponent
  ],
  declarations: [
    ElectionSearchComponent,
    ElectionFormComponent
  ]
})
export class ElectionModule {
}
