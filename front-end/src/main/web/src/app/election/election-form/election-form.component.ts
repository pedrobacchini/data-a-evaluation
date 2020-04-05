import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { environment } from '../../../environments/environment';

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

  private minStartDate = new Date();
  private minEndDate = new Date();
  private ptBR = environment.ptBR;
  private election: Election;

  @Output() electionChange: EventEmitter<Election> = new EventEmitter();
  @Input() set editElection(election: Election) {
    this.election = Object.assign(new Election(), election);
  }

  constructor(private electionService: ElectionService,
              private messageService: MessageService,
              private errorHandler: ErrorHandlerService,
              private datePipe: DatePipe) {
  }

  ngOnInit() {
    this.minStartDate.setDate(this.minStartDate.getDate() + 1);
    this.minEndDate.setDate(this.minEndDate.getDate() + 2);
  }

  save(f: NgForm) {
    this.electionService.save(this.election)
      .subscribe(election => {
        f.reset();
        this.electionChange.emit(election);
        this.messageService.add({severity: 'success', detail: 'Salvo com sucesso'});
      }, exception => this.errorHandler.handle(exception));
  }

  setStartDate($event: any) {
    this.election.startDate = this.datePipe.transform($event, 'dd/MM/yyyy');
  }

  setFinishDate($event: any) {
    this.election.finishDate = this.datePipe.transform($event, 'dd/MM/yyyy');
  }
}
