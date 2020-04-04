import { Component, OnInit } from '@angular/core';
import { Election } from '../election.class';
import { ElectionService } from '../election.service';
import { MessageService } from 'primeng/api';
import { ErrorHandlerService } from '../../core/error-handler.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-election-form',
  templateUrl: './election-form.component.html',
  styleUrls: ['./election-form.component.css']
})
export class ElectionFormComponent implements OnInit {

  election: Election = new Election();

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
        this.messageService.add({severity: 'success', detail: 'Salvo com sucesso'});
      }, exception => this.errorHandler.handle(exception));
  }

  new(f: NgForm) {
    f.reset();
  }
}
