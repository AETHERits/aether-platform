import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MissionCreateRequest } from '../models/mission.model';

export interface ColonyOption {
  idColony: number;
  code: string;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class MissionService {
  private readonly baseUrl = '/api/missions';

  constructor(private http: HttpClient) {}

  createMission(payload: MissionCreateRequest): Observable<any> {
    return this.http.post(this.baseUrl, payload);
  }

  // Non ancora collegato: per ora il component userà dati mock.
  // Quando il backend espone l'endpoint, questo metodo è già pronto.
  getColonies(): Observable<ColonyOption[]> {
    return this.http.get<ColonyOption[]>('/api/colonies');
  }
}
