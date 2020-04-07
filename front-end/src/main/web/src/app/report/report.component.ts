import { Component, OnInit } from '@angular/core';

import { MessageService } from 'primeng/components/common/api';
import * as moment from 'moment';

import { ElectionService } from '../election/election.service';
import { ErrorHandlerService } from '../core/error-handler.service';
import { Election } from '../election/election.class';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  elections: Election[];

  cols: any[];

  constructor(private electionService: ElectionService,
              private errorHandler: ErrorHandlerService,
              private messageService: MessageService) {
    this.electionService.getAllStarted()
      .subscribe(elections => {
        this.elections = elections;
      }, exception => this.errorHandler.handle(exception));
  }

  ngOnInit() {
    this.cols = [
      {field: 'name', header: 'Nome'},
      {field: 'startDate', header: 'Data Inicial'},
      {field: 'finishDate', header: 'Data Final'}
    ];
  }

  electionStatus(election: Election) {
    const now = new Date();
    const startBefore = moment(election.startDate, 'DD/MM/YYYY').isBefore(now);
    const finishBefore = moment(election.finishDate, 'DD/MM/YYYY').isBefore(now);
    return finishBefore ? 'finishElection' : startBefore ? 'startElection' : 'notStartElection';
  }

  electionTooltip(election: Election) {
    const status = this.electionStatus(election);
    return status === 'finishElection' ? 'Eleição terminou' : status === 'startElection' ? 'Eleição começou' : '';
  }

}
