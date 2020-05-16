import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { finalize, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

import { MessageService } from 'primeng/components/common/api';

import { ElectionService } from '../../election/election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';
import { CandidateService } from '../candidate.service';
import { Candidate } from '../candidate.class';

@Component({
  selector: 'app-candidate-form',
  templateUrl: './candidate-form.component.html',
  styleUrls: ['./candidate-form.component.css']
})
export class CandidateFormComponent implements OnInit {

  displayModal: boolean;
  @ViewChild('upload') private upload: ElementRef;
  private fileToUpload: File = null;
  candidate: Candidate = new Candidate();
  elections = [];
  electionPositions = [];
  loading;

  @Output() onSave: EventEmitter<Candidate> = new EventEmitter();

  constructor(private electionService: ElectionService,
              private candidateService: CandidateService,
              private errorHandler: ErrorHandlerService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.electionService.getAllAvailableSelection()
      .subscribe(elections => {
        this.elections = elections.map(election => ({label: election.name, value: election.uuid}));
      }, exception => this.errorHandler.handle(exception));
  }

  save(f: NgForm) {
    this.loading = true;
    this.candidateService.save(this.candidate)
      .pipe(
        finalize(() => this.loading = false),
        switchMap((candidate: Candidate) => {
          if (this.fileToUpload) {
            return this.candidateService
              .uploadPicture(candidate.uuid, this.fileToUpload, this.candidate.picture);
          } else {
            return of(null);
          }
        }, (candidate) => candidate)
      )
      .subscribe((candidate: Candidate) => {
        this.fileToUpload = undefined;
        this.onSave.emit(candidate);
        this.messageService.add({severity: 'success', detail: 'Salvo com sucesso'});
        this.reset(f);
        this.displayModal = false;
      }, exception => this.errorHandler.handle(exception));
  }

  reset(f: NgForm) {
    this.candidate = new Candidate();
    f.reset();
  }

  setElection(uuid: string) {
    this.electionService.getAllElectionPositionsSelection(uuid)
      .subscribe(electionPositionsSelection => {
        this.electionPositions = electionPositionsSelection.map(e => ({label: e.name, value: e.uuid}));
      }, exception => this.errorHandler.handle(exception));
  }

  choose() {
    this.upload.nativeElement.click();
  }

  onFileSelected(target: EventTarget) {
    this.fileToUpload = (<HTMLInputElement>target).files[0];

    const reader = new FileReader();
    reader.readAsDataURL(this.fileToUpload);
    reader.onload = (_event) => {
      this.candidate.picture = reader.result;
    };
  }

  sePictureError(event) {
    event.target.src = '/assets/images/avatars/avatar_2x.png';
  }

  add() {
    this.displayModal = true;
  }

  find(uuid: string) {
    if (uuid) {
      this.displayModal = true;
      this.loading = true;
      this.candidateService.find(uuid)
        .pipe(finalize(() => this.loading = false))
        .subscribe(candidate => {
          this.candidate = candidate;
        }, exception => this.errorHandler.handle(exception));
    }
  }
}
