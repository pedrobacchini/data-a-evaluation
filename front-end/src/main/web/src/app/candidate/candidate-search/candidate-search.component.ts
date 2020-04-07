import { Component, OnInit } from '@angular/core';

import { ElectionService } from '../../election/election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

interface CandidateTable {
  electionName;
  electionPositionName;
  candidateName;
  picture;
}

@Component({
  selector: 'app-candidate-search',
  templateUrl: './candidate-search.component.html',
  styleUrls: ['./candidate-search.component.css']
})
export class CandidateSearchComponent implements OnInit {

  candidatesTable: CandidateTable[] = [];

  rowElectionGroup: any;
  rowElectionPositionGroup: any;

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
                candidateName: candidate.name,
                picture: candidate.picture
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
    this.rowElectionPositionGroup = {};
    if (this.candidatesTable) {
      for (let i = 0; i < this.candidatesTable.length; i++) {
        const rowData = this.candidatesTable[i];
        const electionName = rowData.electionName;
        const electionPositionName = electionName.concat(rowData.electionPositionName);
        if (i === 0) {
          this.rowElectionGroup[electionName] = {index: 0, size: 1};
          this.rowElectionPositionGroup[electionPositionName] = {index: 0, size: 1};
        } else {
          const previousElection = this.candidatesTable[i - 1];
          const previousElectionName = previousElection.electionName;
          const previousElectionPositionName = previousElectionName.concat(previousElection.electionPositionName);
          if (electionName === previousElectionName) {
            this.rowElectionGroup[electionName].size++;
          } else {
            this.rowElectionGroup[electionName] = {index: i, size: 1};
          }
          if (electionPositionName === previousElectionPositionName) {
            this.rowElectionPositionGroup[electionPositionName].size++;
          } else {
            this.rowElectionPositionGroup[electionPositionName] = {index: i, size: 1};
          }
        }
      }
    }
  }

  sePictureError(event) {
    event.target.src = '/assets/images/avatars/avatar_2x.png';
  }
}
