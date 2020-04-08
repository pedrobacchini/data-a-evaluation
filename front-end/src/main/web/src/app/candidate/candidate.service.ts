import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

import { Candidate } from './candidate.class';
import { NewCandidate } from './new-candidate.class';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  candidateUrl: string;

  constructor(private http: HttpClient) {
    this.candidateUrl = `${environment.apiUrl}/candidates`;
  }

  save(newCandidate: NewCandidate): Observable<Candidate> {
    return newCandidate.uuid ? this.update(newCandidate.uuid, newCandidate) : this.create(newCandidate);
  }

  private update(uuid: string, newCandidate: NewCandidate): Observable<Candidate> {
    return this.http.put<Candidate>(`${this.candidateUrl}/${uuid}`, newCandidate);
  }

  private create(newCandidate: NewCandidate): Observable<Candidate> {
    return this.http.post<Candidate>(this.candidateUrl, newCandidate);
  }
}
