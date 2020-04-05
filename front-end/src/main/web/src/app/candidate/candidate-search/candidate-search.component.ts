import { Component, OnInit } from '@angular/core';

import { ElectionService } from '../../election/election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

interface CandidateTable {
  electionName;
  electionPositionName;
  candidateName;
}

@Component({
  selector: 'app-candidate-search',
  templateUrl: './candidate-search.component.html',
  styleUrls: ['./candidate-search.component.css']
})
export class CandidateSearchComponent implements OnInit {

  candidatesTable: CandidateTable[] = [];

  rowElectionGroup: any;

  constructor(private electionService: ElectionService,
              private errorHandler: ErrorHandlerService) {
  }

  ngOnInit() {
    this.electionService.getAll()
      .subscribe(elections => {
        elections.forEach(election => {
          election.electionPositions.forEach(electionPosition => {
            electionPosition.candidates.forEach(candidate => {
              this.candidatesTable.push({
                electionName: election.name,
                electionPositionName: electionPosition.name,
                candidateName: candidate.name
              });
            });
          });
        });
        this.updateRowGroupMetaDataTable();
      }, exception => this.errorHandler.handle(exception));
  }

  onSort() {
    this.updateRowGroupMetaDataTable();
  }

  updateRowGroupMetaDataTable() {
    this.rowElectionGroup = {};
    if (this.candidatesTable) {
      for (let i = 0; i < this.candidatesTable.length; i++) {
        const rowData = this.candidatesTable[i];
        const election = rowData.electionName;
        if (i === 0) {
          this.rowElectionGroup[election] = {index: 0, size: 1};
        } else {
          const previousRowData = this.candidatesTable[i - 1];
          const previousRowGroup = previousRowData.electionName;
          if (election === previousRowGroup) {
            this.rowElectionGroup[election].size++;
          } else {
            this.rowElectionGroup[election] = {index: i, size: 1};
          }
        }
      }
    }
  }

}
