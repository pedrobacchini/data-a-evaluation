import { Component, OnInit, ViewChild } from '@angular/core';
import { finalize } from 'rxjs/operators';

import { ConfirmationService, MessageService } from 'primeng/components/common/api';

import { ElectionService } from '../../election/election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';
import { CandidateService } from '../candidate.service';
import { CandidateFormComponent } from '../candidate-form/candidate-form.component';
import { Candidate } from '../candidate.class';

class CandidateTable {
  uuid: string;
  electionName: string;
  electionPositionName: string;
  candidateName: string;
  picture;
}

@Component({
  selector: 'app-candidate-search',
  templateUrl: './candidate-search.component.html',
  styleUrls: ['./candidate-search.component.css']
})
export class CandidateSearchComponent implements OnInit {

  @ViewChild(CandidateFormComponent) candidateFrom: CandidateFormComponent;
  candidatesTable: CandidateTable[];
  rowElectionGroup: any;
  rowElectionPositionGroup: any;
  loading;

  constructor(private electionService: ElectionService,
              private errorHandler: ErrorHandlerService,
              private confirmation: ConfirmationService,
              private messageService: MessageService,
              private candidateService: CandidateService) {
  }

  ngOnInit() {
    this.candidatesTable = [];
    this.electionService.getAllAvailable()
      .subscribe(elections => {
        elections.forEach(election => {
          election.electionPositions.forEach(electionPosition => {
            electionPosition.candidates.forEach(candidate => {
              this.candidatesTable.push({
                uuid: candidate.uuid,
                electionName: election.name,
                electionPositionName: electionPosition.name,
                candidateName: candidate.name,
                picture: candidate.picture
              });
            });
          });
        });
        this.initRowGroupMetaDataTable();
      }, exception => this.errorHandler.handle(exception));
  }

  onSort() {
    this.initRowGroupMetaDataTable();
  }

  private initRowGroupMetaDataTable() {
    this.sortCandidatesTable();
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

  candidateChange(candidate: Candidate) {
    const candidateTable = {
      uuid: candidate.uuid,
      electionName: candidate.electionPosition.election.name,
      electionPositionName: candidate.electionPosition.name,
      candidateName: candidate.name,
      picture: candidate.picture
    };
    const index = this.candidatesTable.findIndex(c => c.uuid === candidate.uuid);
    console.log(index);
    if (index === -1) {
      this.candidatesTable.push(candidateTable);
    } else {
      this.candidatesTable[index] = candidateTable;
    }
    this.initRowGroupMetaDataTable();
  }

  private sortCandidatesTable() {
    this.candidatesTable.sort((a, b) => {
      return a.electionName.localeCompare(b.electionName) ||
        a.electionPositionName.localeCompare(b.electionPositionName) ||
        a.candidateName.localeCompare(b.candidateName);
    });
  }

  sePictureError(event) {
    event.target.src = '/assets/images/avatars/avatar_2x.png';
  }

  confirmDelete(candidateTable: CandidateTable) {
    this.confirmation.confirm({
      message: 'Tem certeza que deseja excluir?',
      accept: () => {
        this.loading = true;
        this.candidateService.delete(candidateTable.uuid)
          .pipe(finalize(() => this.loading = false))
          .subscribe(() => {
            this.removeCandidate(candidateTable);
            this.messageService.add({severity: 'success', detail: 'Removido com sucesso'});
          }, exception => this.errorHandler.handle(exception));
      }
    });
  }

  private removeCandidate(candidate: CandidateTable) {
    const index = this.candidatesTable.findIndex(c => c.uuid === candidate.uuid);
    if (index > -1) {
      this.candidatesTable.splice(index, 1);
    }
    this.initRowGroupMetaDataTable();
  }
}
