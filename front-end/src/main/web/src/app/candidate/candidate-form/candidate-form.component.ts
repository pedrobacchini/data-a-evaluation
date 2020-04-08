import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { finalize, switchMap } from 'rxjs/operators';

import { MessageService } from 'primeng/components/common/api';

import { NewCandidate } from '../new-candidate.class';
import { ElectionService } from '../../election/election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';
import { CandidateService, UploadUrl } from '../candidate.service';
import { Candidate } from '../candidate.class';

@Component({
  selector: 'app-candidate-form',
  templateUrl: './candidate-form.component.html',
  styleUrls: ['./candidate-form.component.css']
})
export class CandidateFormComponent implements OnInit {

  @ViewChild('upload')
  private upload: ElementRef;
  private newCandidate: NewCandidate = new NewCandidate();
  private elections = [];
  private electionPositions = [];
  private fileToUpload: File = null;
  private loading;
  @Output() candidateChange: EventEmitter<boolean> = new EventEmitter();

  constructor(private electionService: ElectionService,
              private candidateService: CandidateService,
              private errorHandler: ErrorHandlerService,
              private messageService: MessageService) {
    this.electionService.getAllAvailableSummary()
      .subscribe(elections => {
        this.elections = elections.map(election => ({label: election.name, value: election.uuid}));
      }, exception => this.errorHandler.handle(exception));
  }

  ngOnInit() {
  }

  save(f: NgForm) {
    this.loading = true;
    this.candidateService.save(this.newCandidate)
      .pipe(
        finalize(() => this.loading = false),
        switchMap((candidate: Candidate) => {
          return this.candidateService
            .uploadPicture(candidate.uuid, this.fileToUpload, this.newCandidate.picture);
        })
      )
      .subscribe(() => {
        this.candidateChange.emit(true);
        this.messageService.add({severity: 'success', detail: 'Salvo com sucesso'});
        this.reset(f);
      }, exception => this.errorHandler.handle(exception));
  }

  reset(f: NgForm) {
    this.newCandidate = new NewCandidate();
    f.reset();
  }

  setElection(uuid: string) {
    this.electionService.getAllElectionPositionsSummary(uuid)
      .subscribe(electionPositionsResume => {
        this.electionPositions = electionPositionsResume.map(e => ({label: e.name, value: e.uuid}));
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
      this.newCandidate.picture = reader.result;
    };
  }
}
