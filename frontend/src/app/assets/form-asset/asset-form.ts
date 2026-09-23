import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { AssetService } from '../services/asset.service';
import { Asset, CreateAssetRequest } from '../models/asset.model';

@Component({
  selector: 'app-asset-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './asset-form.html',
  styleUrl: './asset-form.scss'
})
export class AssetForm implements OnInit {
  private fb = inject(NonNullableFormBuilder);
  private assetService = inject(AssetService);

  form = this.fb.group({
    codice: ['', [Validators.required, Validators.minLength(1)]],
    nome: ['', [Validators.required, Validators.minLength(1)]],
    criticita_di_default: ['MEDIA', Validators.required]
  });

  prioritaOptions = ['BASSA', 'MEDIA', 'ALTA', 'VITALE'];
  submitting = false;
  creato: string | null = null;
  errore: string | null = null;
  assets: Asset[] = [];

  ngOnInit(): void {
    this.loadAssets();
  }

  loadAssets(): void {
    this.assetService.getAllAssets().subscribe({
      next: (assets) => {
        this.assets = assets;
      },
      error: (err) => {
        console.error('Errore caricamento asset', err);
      }
    });
  }

  submit(): void {
    this.errore = null;
    this.creato = null;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting = true;
    const payload = this.form.getRawValue();

    this.assetService.createOrUpdateAsset(payload as CreateAssetRequest)
      .pipe(
        finalize(() => {
          this.submitting = false;
        })
      )
      .subscribe({
        next: (response) => {
          this.creato = response;
          this.form.reset({ criticita_di_default: 'MEDIA' });
          this.loadAssets();
        },
        error: (err) => {
          console.error('Errore API', err);
          if (err.status === 400 && err.error?.message) {
            this.errore = err.error.message;
          } else if (err.status === 0) {
            this.errore = 'Impossibile contattare il server. Verifica la connessione.';
          } else {
            this.errore = 'Errore durante la creazione/aggiornamento dell\'asset.';
          }
        }
      });
  }

  deleteAsset(id: number): void {
    if (confirm('Sei sicuro di voler eliminare questo asset?')) {
      this.assetService.deleteAsset(id).subscribe({
        next: () => {
          this.loadAssets();
        },
        error: (err) => {
          console.error('Errore eliminazione', err);
          this.errore = 'Errore durante l\'eliminazione dell\'asset.';
        }
      });
    }
  }

  get f() {
    return this.form.controls;
  }
}
