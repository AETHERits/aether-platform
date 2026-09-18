import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AllocazioneRisorsaMissione, StatoAllocazione } from '../models/resource.model';

@Injectable({ providedIn: 'root' })
export class ResourceService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/aether/allocazioni-risorse-missione';

  getAllocazioni(idRequisito?: number): Observable<AllocazioneRisorsaMissione[]> {
    const url = idRequisito ? `${this.apiUrl}?id_requisito=${idRequisito}` : this.apiUrl;
    return this.http.get<AllocazioneRisorsaMissione[]>(url);
  }

  creaAllocazione(data: Partial<AllocazioneRisorsaMissione>): Observable<AllocazioneRisorsaMissione> {
    return this.http.post<AllocazioneRisorsaMissione>(this.apiUrl, data);
  }

  aggiornaStato(idAllocazione: number, nuovoStato: StatoAllocazione): Observable<AllocazioneRisorsaMissione> {
    return this.http.patch<AllocazioneRisorsaMissione>(`${this.apiUrl}/${idAllocazione}/stato`, {
      stato: nuovoStato,
      rilasciata_il: ['RESTITUITA', 'ANNULLATA', 'CONSUMATA'].includes(nuovoStato) ? new Date().toISOString() : null
    });
  }
}
