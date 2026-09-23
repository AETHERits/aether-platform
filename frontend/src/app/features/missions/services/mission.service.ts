import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { MissionCreateRequest, MissionResponse } from '../models/mission.model';

export interface ColonyOption {
  id: number;
  nome: string;
}

@Injectable({
  providedIn: 'root'
})
export class MissionService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/missioni`;

  createMission(payload: MissionCreateRequest): Observable<MissionResponse> {
    return this.http.post<MissionResponse>(this.baseUrl, payload);
  }

  getMissions(): Observable<MissionResponse[]> {
    return this.http.get<MissionResponse[]>(this.baseUrl);
  }

  getMissionById(id: number): Observable<MissionResponse> {
    return this.http.get<MissionResponse>(`${this.baseUrl}/${id}`);
  }

  getColonies(): Observable<ColonyOption[]> {
    return this.http.get<ColonyOption[]>(`${environment.apiUrl}/colonie`);
  }
}
