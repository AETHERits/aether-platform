import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./home/home').then((m) => m.HomePage)
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
  },
  {
    path: 'assets',
    loadComponent: () =>
      import('./assets/form-asset/asset-form').then((m) => m.AssetForm)
  }
  // altre route future: 'missions' (lista), 'missions/:id' (dettaglio), ecc.
];
