import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { AssetService } from '../services/asset.service';
import { Asset, CreateAssetRequest } from '../models/asset.model';

@Component({
  selector: 'app-asset-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './asset-form.html',
  styleUrl: './asset-form.scss'
})
export class AssetForm implements OnInit {
  private fb = inject(NonNullableFormBuilder);
  private assetService = inject(AssetService);
  private location = inject(Location);

  goBack(): void {
    this.location.back();
  }

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

  showForm = false;
  showSearch = false;
  searchTerm = '';

  loading = false;
  sortColumn: 'codice' | 'nome' | 'criticita_di_default' | '' = '';
  sortAsc = true;
  confirmingDelete: number | null = null;
  private deleteTimer: ReturnType<typeof setTimeout> | null = null;

  readonly criticitaLabels: Record<string, string> = {
    BASSA: 'Bassa', MEDIA: 'Media', ALTA: 'Alta', VITALE: 'Vitale'
  };

  label(map: Record<string, string>, value?: string | null): string {
    return value ? (map[value] ?? value) : '';
  }

  ngOnInit(): void {
    this.loadAssets();
  }

  loadAssets(): void {
    this.loading = true;
    this.assetService.getAllAssets()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (assets) => {
          this.assets = assets;
        },
        error: (err) => {
          console.error('Errore caricamento asset', err);
        }
      });
  }

  sortBy(column: 'codice' | 'nome' | 'criticita_di_default'): void {
    if (this.sortColumn === column) {
      this.sortAsc = !this.sortAsc;
    } else {
      this.sortColumn = column;
      this.sortAsc = true;
    }
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    if (this.showForm) {
      this.showSearch = false;
    }
  }

  toggleSearch(): void {
    this.showSearch = !this.showSearch;
    if (this.showSearch) {
      this.showForm = false;
    } else {
      this.searchTerm = '';
    }
  }

  clearSearch(): void {
    this.searchTerm = '';
  }

  get filteredAssets(): Asset[] {
    const term = this.searchTerm.trim().toLowerCase();
    const list = term
      ? this.assets.filter(a =>
          a.codice?.toLowerCase().includes(term) ||
          a.nome?.toLowerCase().includes(term) ||
          a.criticita_di_default?.toLowerCase().includes(term) ||
          this.label(this.criticitaLabels, a.criticita_di_default).toLowerCase().includes(term)
        )
      : [...this.assets];

    if (this.sortColumn) {
      const col = this.sortColumn;
      list.sort((a, b) => {
        const va = (a[col] ?? '').toString().toLowerCase();
        const vb = (b[col] ?? '').toString().toLowerCase();
        return va.localeCompare(vb) * (this.sortAsc ? 1 : -1);
      });
    }
    return list;
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
          this.scheduleSuccessDismiss();
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
    if (this.confirmingDelete !== id) {
      // Primo click: chiedo conferma inline, annulla automaticamente dopo 4 secondi
      this.confirmingDelete = id;
      if (this.deleteTimer) {
        clearTimeout(this.deleteTimer);
      }
      this.deleteTimer = setTimeout(() => (this.confirmingDelete = null), 4000);
      return;
    }

    if (this.deleteTimer) {
      clearTimeout(this.deleteTimer);
    }
    this.confirmingDelete = null;

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

  private scheduleSuccessDismiss(): void {
    setTimeout(() => (this.creato = null), 5000);
  }

  get f() {
    return this.form.controls;
  }
}
