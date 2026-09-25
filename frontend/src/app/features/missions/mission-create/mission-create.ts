import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { MissionService, ColonyOption } from '../services/mission.service';
import { MissionCreateRequest, MissionResponse } from '../models/mission.model';
import { dateRangeValidator } from '../validators/mission.validators';

@Component({
  selector: 'app-mission-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './mission-create.html',
  styleUrl: './mission-create.scss'
})
export class MissionCreate implements OnInit {
  form!: FormGroup;
  submitting = false;
  serverError: string | null = null;

  showForm = false;
  showSearch = false;
  searchTerm = '';

  colonies: ColonyOption[] = [];

  missions: MissionResponse[] = [];
  loading = false;
  successMessage: string | null = null;

  sortColumn: 'code' | 'title' | 'missionType' | 'priority' | 'status' | '' = '';
  sortAsc = true;

  readonly missionTypes = [
    'INTERNAL', 'EVA', 'SCIENCE', 'LOGISTICS',
    'MAINTENANCE', 'RESCUE', 'INSPECTION', 'EXPLORATION', 'TRANSPORT'
  ];
  readonly priorities = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
  readonly safetyLevels = ['STANDARD', 'ELEVATED', 'HIGH_RISK', 'CRITICAL'];

  readonly typeLabels: Record<string, string> = {
    INTERNAL: 'Interna', EVA: 'EVA', SCIENCE: 'Scientifica', LOGISTICS: 'Logistica',
    MAINTENANCE: 'Manutenzione', RESCUE: 'Soccorso', INSPECTION: 'Ispezione',
    EXPLORATION: 'Esplorazione', TRANSPORT: 'Trasporto'
  };
  readonly priorityLabels: Record<string, string> = {
    LOW: 'Bassa', MEDIUM: 'Media', HIGH: 'Alta', CRITICAL: 'Critica'
  };
  readonly safetyLabels: Record<string, string> = {
    STANDARD: 'Standard', ELEVATED: 'Elevato', HIGH_RISK: 'Alto rischio', CRITICAL: 'Critico'
  };
  readonly statusLabels: Record<string, string> = {
    DRAFT: 'Bozza', PLANNED: 'Pianificata', APPROVED: 'Approvata',
    IN_PROGRESS: 'In corso', SUSPENDED: 'Sospesa',
    COMPLETED: 'Completata', CANCELLED: 'Annullata'
  };

  label(map: Record<string, string>, value?: string | null): string {
    return value ? (map[value] ?? value) : '';
  }

  private fb = inject(FormBuilder);
  private missionService = inject(MissionService);
  private location = inject(Location);

  goBack(): void {
    this.location.back();
  }

  ngOnInit(): void {
    this.form = this.fb.group(
      {
        title: ['', [Validators.required, Validators.maxLength(200)]],
        description: [''],
        missionType: ['', Validators.required],
        idOriginColony: [null, Validators.required],
        idDestinationColony: [null],
        destinationName: [''],
        plannedStartAt: ['', Validators.required],
        plannedEndAt: ['', Validators.required],
        priority: ['MEDIUM', Validators.required],
        safetyLevel: ['STANDARD', Validators.required]
      },
      { validators: dateRangeValidator('plannedStartAt', 'plannedEndAt') }
    );
    this.loadColonies();
    this.loadMissions();
  }

  loadColonies(): void {
    this.missionService.getColonies().subscribe({
      next: (colonies) => {
        this.colonies = colonies ?? [];
      },
      error: () => {
        this.colonies = [];
      }
    });
  }

  loadMissions(): void {
    this.loading = true;
    this.missionService.getMissions()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (missions) => {
          this.missions = missions;
        },
        error: (err) => {
          console.error('Errore caricamento missioni', err);
        }
      });
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

  get filteredMissions(): MissionResponse[] {
    const term = this.searchTerm.trim().toLowerCase();
    const list = term
      ? this.missions.filter(m =>
          m.code?.toLowerCase().includes(term) ||
          m.title?.toLowerCase().includes(term) ||
          m.missionType?.toLowerCase().includes(term) ||
          m.priority?.toLowerCase().includes(term) ||
          m.status?.toLowerCase().includes(term) ||
          this.label(this.typeLabels, m.missionType).toLowerCase().includes(term) ||
          this.label(this.priorityLabels, m.priority).toLowerCase().includes(term) ||
          this.label(this.statusLabels, m.status).toLowerCase().includes(term)
        )
      : [...this.missions];

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

  sortBy(column: 'code' | 'title' | 'missionType' | 'priority' | 'status'): void {
    if (this.sortColumn === column) {
      this.sortAsc = !this.sortAsc;
    } else {
      this.sortColumn = column;
      this.sortAsc = true;
    }
  }

  get f() {
    return this.form.controls;
  }

  private scheduleSuccessDismiss(): void {
    setTimeout(() => (this.successMessage = null), 5000);
  }

  onSubmit(): void {
    this.serverError = null;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting = true;

    const raw = this.form.getRawValue();
    const payload: MissionCreateRequest = {
      ...raw,
      plannedStartAt: toIsoDateTime(raw.plannedStartAt),
      plannedEndAt: toIsoDateTime(raw.plannedEndAt)
    };

    this.missionService.createMission(payload)
      .pipe(
        finalize(() => {
          this.submitting = false;
        })
      )
      .subscribe({
        next: (response) => {
          this.successMessage = response?.code
            ? `Missione ${response.code} creata in bozza con successo.`
            : 'Missione creata in bozza con successo.';
          this.scheduleSuccessDismiss();
          this.form.reset({
            priority: 'MEDIUM',
            safetyLevel: 'STANDARD'
          });
          this.loadMissions();
        },
        error: (err) => {
          if (err.status === 409) {
            this.serverError = 'Conflitto: impossibile creare la missione con questi dati (verificare colonia e finestra temporale).';
          } else if (err.status === 400 && err.error?.message) {
            this.serverError = err.error.message;
          } else if (err.status === 0) {
            this.serverError = 'Impossibile contattare il server. Verifica la connessione.';
          } else {
            this.serverError = 'Errore imprevisto durante la creazione della missione.';
          }
        }
      });
  }
}

function toIsoDateTime(value: string): string {
  if (!value) {
    return value;
  }
  if (value.endsWith('Z') || /[+-]\d{2}:\d{2}$/.test(value)) {
    return value;
  }
  const parsed = new Date(value);
  return Number.isNaN(parsed.getTime()) ? value : parsed.toISOString();
}
