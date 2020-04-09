import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

import { UtilsService } from '../core/utils.service';
import { Candidate } from './candidate.class';

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

  save(candidate: Candidate): Observable<Candidate> {
    return candidate.uuid ? this.update(candidate.uuid, candidate) : this.create(candidate);
  }

  private update(uuid: string, candidate: Candidate): Observable<Candidate> {
    return this.http.put<Candidate>(`${this.candidateUrl}/${uuid}`, candidate);
  }

  private create(candidate: Candidate): Observable<Candidate> {
    return this.http.post<Candidate>(this.candidateUrl, candidate);
  }

  uploadPicture(uuid: string, file: File, url: string): Observable<UploadUrl> {
    const formData: FormData = new FormData();
    formData.append('file', this.utilsService.dataURItoBlob(url, file.type), file.name);
    return this.http.put<UploadUrl>(`${this.candidateUrl}/${uuid}/picture`, formData);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.candidateUrl}/${uuid}`);
  }

  find(uuid: string): Observable<Candidate> {
    return this.http.get<Candidate>(`${this.candidateUrl}/${uuid}`);
  }
}
