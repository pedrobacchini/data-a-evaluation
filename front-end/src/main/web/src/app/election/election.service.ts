import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Election } from './election.class';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ElectionService {

  electionUrl: string;

  constructor(private http: HttpClient) {
    this.electionUrl = `${environment.apiUrl}/elections`;
  }

  save(election: Election): Observable<Election> {
    return this.http.post<Election>(this.electionUrl, election);
  }

  getAll(): Observable<Election[]> {
    return this.http.get<Election[]>(this.electionUrl);
  }
}
