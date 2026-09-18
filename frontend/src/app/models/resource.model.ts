export type StatoAllocazione = 'RISERVATA' | 'CONSUMATA' | 'RESTITUITA' | 'ANNULLATA';

export interface AllocazioneRisorsaMissione {
  id_allocazione?: number;
  id_requisito: number;
  id_habitat: number;
  id_risorsa: number;
  id_lotto: number;
  quantita_allocata: number;
  stato: StatoAllocazione;
  allocata_il?: string;
  rilasciata_il?: string | null;
}
