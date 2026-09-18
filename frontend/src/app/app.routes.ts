import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'missions/new',
    loadComponent: () =>
      import('./features/missions/mission-create/mission-create').then(
        (m) => m.MissionCreate
      )
  }
  // altre route future: 'missions' (lista), 'missions/:id' (dettaglio), ecc.
];
