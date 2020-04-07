import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { NewCandidate } from '../new-candidate.class';
import { ElectionService } from '../../election/election.service';
import { ErrorHandlerService } from '../../core/error-handler.service';

@Component({
  selector: 'app-candidate-form',
  templateUrl: './candidate-form.component.html',
  styleUrls: ['./candidate-form.component.css']
})
export class CandidateFormComponent implements OnInit {

  newCandidate: NewCandidate = new NewCandidate();
  elections = [];
  electionPositions = [];

  constructor(private electionService: ElectionService,
              private errorHandler: ErrorHandlerService) {
    this.electionService.getAllAvailableResume()
      .subscribe(elections => {
        this.elections = elections.map(election => ({label: election.name, value: election.uuid}));
      }, exception => this.errorHandler.handle(exception));
  }

  ngOnInit() {
  }

  save(f: NgForm) {

  }

  reset(f: NgForm) {

  }

  setElection(uuid: string) {
    this.electionService.getAllElectionPositionsResume(uuid)
      .subscribe(electionPositionsResume => {
        this.electionPositions = electionPositionsResume.map(e => ({label: e.name, value: e.uuid}));
      }, exception => this.errorHandler.handle(exception));
  }

  choose() {
    console.log('choose');
  }

  fileChangeEvent($event: Event) {
    console.log('fileChangeEvent');
  }
}
