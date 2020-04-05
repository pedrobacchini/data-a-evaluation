import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TableModule } from 'primeng/components/table/table';

import { CandidateSearchComponent } from './candidate-search/candidate-search.component';

@NgModule({
  declarations: [
    CandidateSearchComponent
  ],
  imports: [
    CommonModule,

    TableModule
  ]
})
export class CandidateModule {
}
