import { DatePipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Colonia, IncidenteCreato, IncidentiService } from '../incidenti.service';

@Component({
  selector: 'app-incidenti-form',
  imports: [ReactiveFormsModule, DatePipe],
  templateUrl: './incidenti-form.html'
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
  creato: IncidenteCreato | null = null;

  ngOnInit(): void {
    this.service.getColonie().subscribe(c => (this.colonie = c));
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
      },
      error: e => console.error('Errore API', e)
    });
  }
}
