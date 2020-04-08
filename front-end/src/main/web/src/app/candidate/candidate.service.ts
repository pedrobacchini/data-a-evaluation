import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

import { Candidate } from './candidate.class';
import { NewCandidate } from './new-candidate.class';
import { UtilsService } from '../core/utils.service';

export class UploadUrl {
  url: string;
}

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  candidateUrl: string;

  constructor(private http: HttpClient,
              private utilsService: UtilsService) {
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

  uploadPicture(uuid: string, file: File, url: string): Observable<UploadUrl> {
    const formData: FormData = new FormData();
    formData.append('file', this.utilsService.dataURItoBlob(url, file.type), file.name);
    return this.http.put<UploadUrl>(`${this.candidateUrl}/${uuid}/picture`, formData);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.candidateUrl}/${uuid}`);
  }
}
