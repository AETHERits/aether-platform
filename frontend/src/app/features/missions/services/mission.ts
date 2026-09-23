import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MissionCreateRequest, MissionResponse } from '../models/mission.model';

export interface ColonyOption {
  idColony: number;
  code: string;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class MissionService {
  private readonly baseUrl = 'http://localhost:8080/api/missioni';

  constructor(private http: HttpClient) {}

  createMission(payload: MissionCreateRequest): Observable<any> {
    return this.http.post(this.baseUrl, payload);
  }

  getMissions(): Observable<MissionResponse[]> {
    return this.http.get<MissionResponse[]>(this.baseUrl);
  }

  getMissionById(id: number): Observable<MissionResponse> {
    return this.http.get<MissionResponse>(`${this.baseUrl}/${id}`);
  }

  getColonies(): Observable<ColonyOption[]> {
    return this.http.get<ColonyOption[]>('http://localhost:8080/api/colonies');
  }
}
