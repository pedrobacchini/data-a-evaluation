import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { Election } from './election.class';

@Injectable({
  providedIn: 'root'
})
export class ElectionService {

  electionUrl: string;

  constructor(private http: HttpClient) {
    this.electionUrl = `${environment.apiUrl}/elections`;
  }

  save(election: Election): Observable<Election> {
    return election.uuid ? this.update(election.uuid, election) : this.create(election);
  }

  private update(uuid: string, election: Election): Observable<Election> {
    return this.http.put<Election>(`${this.electionUrl}/${uuid}`, election);
  }

  private create(election: Election): Observable<Election> {
    return this.http.post<Election>(this.electionUrl, election);
  }

  getAll(): Observable<Election[]> {
    return this.http.get<Election[]>(this.electionUrl);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.electionUrl}/${uuid}`);
  }
}
