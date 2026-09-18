import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AllocazioneRisorsaMissione, StatoAllocazione } from '../../models/resource.model';
import { ResourceService } from '../../services/resource.service';

@Component({
  selector: 'app-resource-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './resource-catalog.component.html',
  styleUrl: './resource-catalog.component.css'
})
export class ResourceCatalogComponent implements OnInit {
  private readonly resourceService = inject(ResourceService);

  allocazioni: AllocazioneRisorsaMissione[] = [];
  statiDisponibili: StatoAllocazione[] = ['RISERVATA', 'CONSUMATA', 'RESTITUITA', 'ANNULLATA'];

  nuovaAllocazione: Partial<AllocazioneRisorsaMissione> = {
    id_requisito: undefined,
    id_habitat: undefined,
    id_risorsa: undefined,
    id_lotto: undefined,
    quantita_allocata: undefined,
    stato: 'RISERVATA'
  };

  ngOnInit(): void {
    this.caricaAllocazioni();
  }

  caricaAllocazioni(): void {
    this.resourceService.getAllocazioni().subscribe(data => this.allocazioni = data);
  }

  salvaAllocazione(): void {
    if (
      !this.nuovaAllocazione.id_requisito ||
      !this.nuovaAllocazione.id_habitat ||
      !this.nuovaAllocazione.id_risorsa ||
      !this.nuovaAllocazione.id_lotto ||
      !this.nuovaAllocazione.quantita_allocata ||
      this.nuovaAllocazione.quantita_allocata <= 0
    ) {
      return;
    }

    this.resourceService.creaAllocazione(this.nuovaAllocazione).subscribe(() => {
      this.nuovaAllocazione = {
        id_requisito: undefined,
        id_habitat: undefined,
        id_risorsa: undefined,
        id_lotto: undefined,
        quantita_allocata: undefined,
        stato: 'RISERVATA'
      };
      this.caricaAllocazioni();
    });
  }

  cambiaStato(idAllocazione: number, nuovoStato: StatoAllocazione): void {
    this.resourceService.aggiornaStato(idAllocazione, nuovoStato).subscribe(() => {
      this.caricaAllocazioni();
    });
  }
}
