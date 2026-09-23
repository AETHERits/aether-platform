# Modulo Asset

Questo modulo gestisce i form e le operazioni CRUD relative agli asset nel sistema AETHER.

## Struttura

- **models/**: Contiene i modelli TypeScript (Asset, CreateAssetRequest)
- **services/**: Contiene il servizio AssetService per le API calls
- **form-asset/**: Contiene il componente form per la gestione degli asset

## Componente: AssetForm

### Funzionalità
- Aggiunta/Aggiornamento di asset
- Visualizzazione lista asset esistenti
- Eliminazione asset
- Gestione errori e messaggi di successo

### Utilizzo

```typescript
import { AssetForm } from './assets/form-asset/asset-form';

// Nel routing
{
  path: 'assets',
  loadComponent: () => import('./assets/form-asset/asset-form').then(m => m.AssetForm)
}
```

### Form Fields
- **Codice** (richiesto): Codice univoco dell'asset
- **Nome** (richiesto): Nome descrittivo dell'asset
- **Criticità di Default** (richiesto): Livello di priorità predefinita
  - BASSA
  - MEDIA
  - ALTA
  - VITALE

## API Endpoint

Tutti gli endpoint sono esposti via `AssetService`:

- `GET /api/assets` - Ottieni tutti gli asset
- `GET /api/assets/{id}` - Ottieni un asset specifico
- `POST /api/assets/aggiungi-asset` - Crea o aggiorna un asset
- `DELETE /api/assets/cancella/{id}` - Elimina un asset

## Styling

Il componente usa SCSS con:
- Layout responsive (grid 2 colonne, 1 colonna su mobile)
- Stili personalizzati per badge di criticità
- Animazioni smooth su hover
- Validazione visiva dei form
