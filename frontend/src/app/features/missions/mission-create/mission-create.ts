import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { MissionService, ColonyOption } from '../services/mission';
import { MissionCreateRequest, MissionResponse } from '../models/mission.model';
import { dateRangeValidator } from '../validators/mission.validators';

@Component({
  selector: 'app-mission-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './mission-create.html',
  styleUrl: './mission-create.scss'
})
export class MissionCreate implements OnInit {
  form!: FormGroup;
  submitting = false;
  serverError: string | null = null;

  colonies: ColonyOption[] = [
    { idColony: 1, code: 'ARES-PRIME', name: 'Ares Prime' },
    { idColony: 2, code: 'VALLES-RO', name: 'Valles Research Outpost' },
    { idColony: 3, code: 'ELYSIUM-RELAY', name: 'Elysium Relay' }
  ];

  missions: MissionResponse[] = [];

  readonly missionTypes = [
    'INTERNAL', 'EVA', 'SCIENCE', 'LOGISTICS',
    'MAINTENANCE', 'RESCUE', 'INSPECTION', 'EXPLORATION', 'TRANSPORT'
  ];
  readonly priorities = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
  readonly safetyLevels = ['STANDARD', 'ELEVATED', 'HIGH_RISK', 'CRITICAL'];

  constructor(
    private fb: FormBuilder,
    private missionService: MissionService
  ) {}

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
    this.loadMissions();
  }

  loadMissions(): void {
    this.missionService.getMissions().subscribe({
      next: (missions) => {
        this.missions = missions;
      },
      error: (err) => {
        console.error('Errore caricamento missioni', err);
      }
    });
  }

  get f() {
    return this.form.controls;
  }

  onSubmit(): void {
    this.serverError = null;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitting = true;

    const payload: MissionCreateRequest = this.form.value;

    this.missionService.createMission(payload)
      .pipe(
        finalize(() => {
          this.submitting = false;
        })
      )
      .subscribe({
        next: (response) => {
          console.log('Missione creata:', response);
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
