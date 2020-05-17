import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, flatMap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { Election } from './election.class';
import { ElectionStarted } from './election-started.class';
import { ElectionSelection } from './election-selection.class';
import { ElectionPositionSelection } from './election-position-selection.class';

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
    return this.http.put<void>(`${this.electionUrl}/${uuid}`, election)
      .pipe(map(() => election));
  }

  private create(election: Election): Observable<Election> {
    return this.http.post(this.electionUrl, election, {observe: 'response'})
      .pipe(
        flatMap((response) => {
          const location = response.headers.get('Location');
          return this.http.get<Election>(location);
        })
      );
  }

  getAllStarted(): Observable<ElectionStarted[]> {
    return this.http.get<ElectionStarted[]>(this.electionUrl + '?started');
  }

  getAllAvailable(): Observable<Election[]> {
    return this.http.get<Election[]>(this.electionUrl + '?available');
  }

  getAllAvailableSelection(): Observable<ElectionSelection[]> {
    return this.http.get<ElectionSelection[]>(this.electionUrl + '?available&selection');
  }

  getAllElectionPositionsSelection(uuid: string): Observable<ElectionPositionSelection[]> {
    return this.http.get<ElectionPositionSelection[]>(`${this.electionUrl}/${uuid}/election-positions?selection`);
  }

  delete(uuid: string): Observable<void> {
    return this.http.delete<void>(`${this.electionUrl}/${uuid}`);
  }
}
