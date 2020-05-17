import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { map, flatMap } from 'rxjs/operators';

import { UtilsService } from '../core/utils.service';
import { Candidate } from './candidate.class';

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
    return this.http.put<void>(`${this.candidateUrl}/${uuid}`, candidate)
      .pipe(map(() => candidate));
  }

  private create(candidate: Candidate): Observable<Candidate> {
    return this.http.post(this.candidateUrl, candidate, {observe: 'response', responseType: 'text'})
      .pipe(
        flatMap((response) => {
          const location = response.headers.get('Location');
          return this.http.get<Candidate>(location);
        })
      );
  }

  uploadPicture(uuid: string, file: File, url: string): Observable<void> {
    const formData: FormData = new FormData();
    formData.append('file', this.utilsService.dataURItoBlob(url, file.type), file.name);
    return this.http.put<void>(`${this.candidateUrl}/${uuid}/picture`, formData);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.candidateUrl}/${uuid}`);
  }

  find(uuid: string): Observable<Candidate> {
    return this.http.get<Candidate>(`${this.candidateUrl}/${uuid}`);
  }
}
