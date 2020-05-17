import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { finalize, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';

import { MessageService } from 'primeng/components/common/api';

import { ElectionService } from '../../election/election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';
import { CandidateService } from '../candidate.service';
import { Candidate } from '../candidate.class';
import { ElectionSelection } from '../../election/election-selection.class';
import { ElectionPositionSelection } from '../../election/election-position-selection.class';

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
  elections: ElectionSelection[] = [];
  electionPositions: ElectionPositionSelection[] = [];
  loading;

  @Output() onSave: EventEmitter<Candidate> = new EventEmitter();
  electionSelected: ElectionSelection;
  electionPositionSelected: ElectionPositionSelection;

  constructor(private electionService: ElectionService,
              private candidateService: CandidateService,
              private errorHandler: ErrorHandlerService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.electionService.getAllAvailableSelection()
      .subscribe(elections => {
        this.elections = elections;
      }, exception => this.errorHandler.handle(exception));
  }

  save(f: NgForm) {
    this.loading = true;
    this.candidateService.save(this.candidate)
      .pipe(
        finalize(() => this.loading = false),
        switchMap((candidate: Candidate) => {
          if (this.fileToUpload) {
            return this.candidateService.uploadPicture(candidate.uuid, this.fileToUpload, this.candidate.picture);
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

  loadElectionPositions(electionSelection: ElectionSelection) {
    this.electionService.getAllElectionPositionsSelection(electionSelection.uuid)
      .subscribe(electionPositionsSelection => {
        this.electionPositions = electionPositionsSelection;
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
          this.electionSelected = ElectionSelection.build(candidate.electionPosition.election);
          this.electionPositionSelected = ElectionPositionSelection.build(candidate.electionPosition);
          if (this.electionSelected) {
            this.loadElectionPositions(this.electionSelected);
          }
        }, exception => this.errorHandler.handle(exception));
    }
  }

  setElectionPositions(electionPositionSelection: ElectionPositionSelection) {
    this.candidate.electionPosition.uuid = electionPositionSelection.uuid;
    this.candidate.electionPosition.name = electionPositionSelection.name;
    this.candidate.electionPosition.election.uuid = this.electionSelected.uuid;
    this.candidate.electionPosition.election.name = this.electionSelected.name;
  }
}
