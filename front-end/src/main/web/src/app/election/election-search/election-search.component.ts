import { Component, OnInit } from '@angular/core';
import { Election } from '../election.class';
import { ElectionService } from '../election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-election-search',
  templateUrl: './election-search.component.html',
  styleUrls: ['./election-search.component.css']
})
export class ElectionSearchComponent implements OnInit {

  elections: Election[];
  cols: any[];

  constructor(private electionService: ElectionService,
              private errorHandler: ErrorHandlerService) {
    this.electionService.getAll()
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

}
