import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';

import { MessageService } from 'primeng/components/common/api';

import { Election } from '../election.class';
import { ElectionService } from '../election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-election-form',
  templateUrl: './election-form.component.html',
  styleUrls: ['./election-form.component.css']
})
export class ElectionFormComponent implements OnInit {

  election: Election = new Election();
  @Output() newElection: EventEmitter<Election> = new EventEmitter();

  constructor(private electionService: ElectionService,
              private messageService: MessageService,
              private errorHandler: ErrorHandlerService) {
  }

  ngOnInit() {
  }

  save(f: NgForm) {
    this.electionService.save(this.election)
      .subscribe(election => {
        f.reset();
        this.newElection.emit(election);
        this.messageService.add({severity: 'success', detail: 'Salvo com sucesso'});
      }, exception => this.errorHandler.handle(exception));
  }

  new(f: NgForm) {
    f.reset();
  }
}
