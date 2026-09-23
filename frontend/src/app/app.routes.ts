import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/home/home').then((m) => m.HomePage)
  },
  {
    path: 'incidenti/nuovo',
    loadComponent: () =>
      import('./features/incidenti/incidenti-form').then((m) => m.IncidentiForm)
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
      import('./features/assets/asset-form/asset-form').then((m) => m.AssetForm)
  }
];
