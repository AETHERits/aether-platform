import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Asset, CreateAssetRequest } from '../models/asset.model';

@Injectable({
  providedIn: 'root'
})
export class AssetService {
  private apiUrl = 'http://localhost:8080/api/assets';

  constructor(private http: HttpClient) {}

  getAllAssets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(this.apiUrl);
  }

  getAssetById(id: number): Observable<Asset> {
    return this.http.get<Asset>(`${this.apiUrl}/${id}`);
  }

  createOrUpdateAsset(asset: Asset | CreateAssetRequest): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/aggiungi-asset`, asset);
  }

  deleteAsset(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/cancella/${id}`);
  }
}
