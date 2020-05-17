import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { environment } from '../../../environments/environment';
import { finalize } from 'rxjs/operators';

import { MessageService } from 'primeng/components/common/api';
import nestedObjectAssign from 'nested-object-assign';

import { Election } from '../election.class';
import { ElectionService } from '../election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-election-form',
  templateUrl: './election-form.component.html',
  styleUrls: ['./election-form.component.css']
})
export class ElectionFormComponent implements OnInit {

  displayModal: boolean;
  minStartDate = new Date();
  minEndDate = new Date();
  ptBR = environment.ptBR;
  election: Election = new Election();
  electionPositions: string[];
  loading;

  @Output() onSave: EventEmitter<Election> = new EventEmitter();

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
    this.loading = true;
    this.electionService.save(this.election)
      .pipe(finalize(() => this.loading = false))
      .subscribe(election => {
        this.reset(f);
        this.onSave.emit(election);
        this.messageService.add({severity: 'success', detail: 'Salvo com sucesso'});
        this.displayModal = false;
      }, exception => this.errorHandler.handle(exception));
  }

  setStartDate($event: any) {
    this.election.startDate = this.datePipe.transform($event, 'dd/MM/yyyy');
  }

  setFinishDate($event: any) {
    this.election.finishDate = this.datePipe.transform($event, 'dd/MM/yyyy');
  }

  addElectionPosition(value: string) {
    this.election.electionPositions.push({name: value});
  }

  removeElectionPosition(value: string) {
    const index = this.election.electionPositions.findIndex(electionPosition => electionPosition.name === value);
    if (index > -1) {
      this.election.electionPositions.splice(index, 1);
    }
  }

  reset(f: NgForm) {
    this.electionPositions = undefined;
    this.election = new Election();
    f.reset();
  }

  add() {
    this.displayModal = true;
  }

  find(election: Election) {
    this.displayModal = true;
    this.election = nestedObjectAssign(new Election(), election);
    this.electionPositions = election.electionPositions.map(e => e.name);
  }
}
