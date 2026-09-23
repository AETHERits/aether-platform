export interface Asset {
  id_tipo_asset: number;
  codice: string;
  nome: string;
  criticita_di_default: 'BASSA' | 'MEDIA' | 'ALTA' | 'VITALE';
}

export interface CreateAssetRequest {
  codice: string;
  nome: string;
  criticita_di_default: 'BASSA' | 'MEDIA' | 'ALTA' | 'VITALE';
}
