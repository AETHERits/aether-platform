import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'missions/new',
    pathMatch: 'full'
  },
  {
    path: 'missions/new',
    loadComponent: () =>
      import('./features/missions/mission-create/mission-create').then(
        (m) => m.MissionCreate
      )
  }
];
