import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Asset, CreateAssetRequest } from '../models/asset.model';

@Injectable({
  providedIn: 'root'
})
export class AssetService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/assets`;

  getAllAssets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(this.apiUrl);
  }

  getAssetById(id: number): Observable<Asset> {
    return this.http.get<Asset>(`${this.apiUrl}/${id}`);
  }

  createOrUpdateAsset(asset: Asset | CreateAssetRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/aggiungi-asset`, asset, { responseType: 'text' });
  }

  deleteAsset(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/cancella/${id}`, { responseType: 'text' });
  }
}
