import { Component, OnInit } from '@angular/core';

import { ConfirmationService, MessageService } from 'primeng/components/common/api';

import { Election } from '../election.class';
import { ElectionService } from '../election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-election-search',
  templateUrl: './election-search.component.html',
  styleUrls: ['./election-search.component.css']
})
export class ElectionSearchComponent implements OnInit {

  elections = new Map<string, Election>();
  cols: any[];
  editElection: Election = new Election();

  constructor(private electionService: ElectionService,
              private errorHandler: ErrorHandlerService,
              private messageService: MessageService,
              private confirmation: ConfirmationService) {
    this.electionService.getAllAvailable()
      .subscribe(elections => {
        elections.map(election => {
          this.elections.set(election.uuid, election);
        });
      }, exception => this.errorHandler.handle(exception));
  }

  ngOnInit() {
    this.cols = [
      {field: 'name', header: 'Nome'},
      {field: 'startDate', header: 'Data Inicial'},
      {field: 'finishDate', header: 'Data Final'}
    ];
  }

  electionChange(newElection: Election) {
    this.elections.set(newElection.uuid, newElection);
  }

  confirmDelete(election: Election) {
    this.confirmation.confirm({
      message: 'Tem certeza que deseja excluir?',
      accept: () => {
        this.electionService.delete(election.uuid)
          .subscribe(() => {
            this.elections.delete(election.uuid);
            this.messageService.add({severity: 'success', detail: 'Removido com sucesso'});
          }, exception => this.errorHandler.handle(exception));
      }
    });
  }

  electionsAtTheTable() {
    return Array.from(this.elections.values());
  }
}
