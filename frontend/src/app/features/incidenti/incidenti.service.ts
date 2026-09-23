import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Colonia {
  id: number;
  nome: string;
}

export interface NuovoIncidente {
  idColonia: number;
  idMissione?: number | null;
  idHabitat?: number | null;
  idAsset?: number | null;
  titolo: string;
  descrizione: string;
  severita: string;
  riportatoDa: number;
}

export interface IncidenteCreato {
  id: number;
  codice: string;
  stato: string;
  registratoIl: string;
}

export interface Incidente {
  id: number;
  codice: string;
  idColonia: number;
  titolo: string;
  descrizione: string;
  severita: string;
  stato: string;
  registratoIl: string;
}

@Injectable({ providedIn: 'root' })
export class IncidentiService {
  private http = inject(HttpClient);
  private readonly url = environment.apiUrl;

  getColonie(): Observable<Colonia[]> {
    return this.http.get<Colonia[]>(`${this.url}/colonie`);
  }

  creaIncidente(dto: NuovoIncidente): Observable<IncidenteCreato> {
    return this.http.post<IncidenteCreato>(`${this.url}/incidenti`, dto);
  }

  getIncidenti(): Observable<Incidente[]> {
    return this.http.get<Incidente[]>(`${this.url}/incidenti`);
  }
}
