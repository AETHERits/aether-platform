import { DatePipe, CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Colonia, IncidenteCreato, Incidente, IncidentiService } from './incidenti.service';

@Component({
  selector: 'app-incidenti-form',
  imports: [ReactiveFormsModule, DatePipe, CommonModule],
  templateUrl: './incidenti-form.html',
  styleUrl: './incidenti-form.scss'
})
export class IncidentiForm implements OnInit {
  private fb = inject(NonNullableFormBuilder);
  private service = inject(IncidentiService);

  form = this.fb.group({
    idColonia:   [null as number | null, Validators.required],
    titolo:      ['', Validators.required],
    descrizione: ['', Validators.required],
    severita:    [null as string | null, Validators.required]
  });

  severitaOptions = ['BASSA', 'MEDIA', 'ALTA', 'CRITICA'];
  colonie: Colonia[] = [];
  incidenti: Incidente[] = [];
  creato: IncidenteCreato | null = null;

  ngOnInit(): void {
    this.service.getColonie().subscribe(c => (this.colonie = c));
    this.loadIncidenti();
  }

  loadIncidenti(): void {
    this.service.getIncidenti().subscribe({
      next: (inc) => {
        this.incidenti = inc;
      },
      error: (e) => console.error('Errore caricamento incidenti', e)
    });
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const v = this.form.getRawValue();
    this.service.creaIncidente({
      idColonia: v.idColonia!,
      titolo: v.titolo,
      descrizione: v.descrizione,
      severita: v.severita!,
      riportatoDa: 1
    }).subscribe({
      next: ris => {
        this.creato = ris;
        this.form.reset();
        this.loadIncidenti();
      },
      error: e => console.error('Errore API', e)
    });
  }
}
