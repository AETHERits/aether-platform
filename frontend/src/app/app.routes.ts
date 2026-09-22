import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'missions/new',
    pathMatch: 'full'
  },
  {
    path: '',
    redirectTo: 'incidenti/nuovo',
    pathMatch: 'full'
  },
  {
    path: 'incidenti/nuovo',
    loadComponent: () =>
      import('./incidenti/form-incidenti/incidenti-form').then((m) => m.IncidentiForm)
  },
  {
    path: 'missions/new',
    loadComponent: () =>
      import('./features/missions/mission-create/mission-create').then(
        (m) => m.MissionCreate
      )
  }
  // altre route future: 'missions' (lista), 'missions/:id' (dettaglio), ecc.
];
