# Modulo Asset

Questo modulo gestisce i form e le operazioni CRUD relative agli asset nel sistema AETHER.

## Struttura

- **models/**: Contiene i modelli TypeScript (Asset, CreateAssetRequest)
- **services/**: Contiene il servizio AssetService per le chiamate API
- **form-asset/**: Contiene il componente form per la gestione degli asset

## Componente: AssetForm

### Funzionalità
- Aggiunta/Aggiornamento di asset
- Visualizzazione lista asset esistenti
- Eliminazione asset
- Gestione errori e messaggi di successo

### Utilizzo

```typescript
import { AssetForm } from './features/assets/asset-form/asset-form';

// Nel routing
{
  path: 'assets',
  loadComponent: () => import('./features/assets/asset-form/asset-form').then(m => m.AssetForm)
}
```

### Campi del form
- **Codice** (richiesto): Codice univoco dell'asset
- **Nome** (richiesto): Nome descrittivo dell'asset
- **Criticità di Default** (richiesto): Livello di priorità predefinita
  - BASSA
  - MEDIA
  - ALTA
  - VITALE

## Endpoint API

Tutti gli endpoint sono esposti via `AssetService`:

- `GET /api/assets` - Ottieni tutti gli asset
- `GET /api/assets/{id}` - Ottieni un asset specifico
- `POST /api/assets/aggiungi-asset` - Crea o aggiorna un asset
- `DELETE /api/assets/cancella/{id}` - Elimina un asset

## Stili

Il componente usa SCSS con:
- Layout adattivo (griglia a 2 colonne, 1 colonna su mobile)
- Stili personalizzati per i badge di criticità
- Animazioni fluide al passaggio del mouse
- Validazione visiva dei form
