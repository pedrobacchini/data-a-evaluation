import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { TableModule } from 'primeng/components/table/table';
import { InputTextModule } from 'primeng/components/inputtext/inputtext';
import { DropdownModule } from 'primeng/components/dropdown/dropdown';
import { ButtonModule } from 'primeng/components/button/button';

import { CandidateSearchComponent } from './candidate-search/candidate-search.component';
import { CandidateFormComponent } from './candidate-form/candidate-form.component';

@NgModule({
  declarations: [
    CandidateSearchComponent,
    CandidateFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,

    TableModule,
    InputTextModule,
    DropdownModule,
    ButtonModule
  ]
})
export class CandidateModule {
}
