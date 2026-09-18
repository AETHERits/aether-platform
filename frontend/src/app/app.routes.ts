import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'incidenti/nuovo',
    pathMatch: 'full'
  },
  {
    path: 'incidenti/nuovo',
    loadComponent: () =>
      import('./incidenti/form-incidenti/incidenti-form').then(m => m.IncidentiForm)
  }
];
