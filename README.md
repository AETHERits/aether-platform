# AETHER — Mars Operations Platform

> Piattaforma digitale unificata per la gestione operativa di colonie marziane: missioni, equipaggi, risorse, manutenzione, incidenti, scienza e telemetria.

Progetto capstone Full-Stack sviluppato in modalità **simulazione aziendale Agile/Scrum**, con backlog reale, squad cross-functional, Pull Request e Code Review obbligatorie.

---

## 📌 Indice

- [Descrizione del progetto](#descrizione-del-progetto)
- [Stack tecnologico](#stack-tecnologico)
- [Requisiti e versioni](#requisiti-e-versioni)
- [Avvio rapido](#avvio-rapido)
- [Architettura e domini funzionali](#architettura-e-domini-funzionali)
- [Le 5 Squad](#le-5-squad)
- [Organizzazione del lavoro](#organizzazione-del-lavoro)
- [Git Workflow](#git-workflow)
- [Scrum Board](#scrum-board)
- [Definition of Ready / Done](#definition-of-ready--done)
- [Cerimonie Scrum](#cerimonie-scrum)
- [Roadmap](#roadmap)
- [Documentazione del repository](#documentazione-del-repository)

---

## Descrizione del progetto

**AETHER** è la piattaforma richiesta dall'*Aether Frontier Consortium (AFC)* per centralizzare le operazioni di colonie marziane, oggi gestite con strumenti separati (fogli di calcolo, telemetria verticale, procedure manuali).

Il sistema copre dieci domini funzionali:

| Dominio | Responsabilità |
|---|---|
| Colony & Habitat Management | Colonie, habitat, moduli, asset tecnici |
| Crew & Skills | Persone, competenze, certificazioni, disponibilità |
| Mission Operations | Missioni, fasi, equipaggi, mezzi, stato |
| Resource & Logistics | Scorte, lotti, movimenti, rifornimenti |
| Maintenance & Incidents | Asset, guasti, ticket, interventi |
| Science & Samples | Esperimenti, laboratori, campioni |
| Telemetry & Events | Sensori, misure, eventi, alert |
| Communication & Alerts | Notifiche, escalation, presa visione |
| Analytics & Reporting | Indicatori, trend, dataset analitici |
| Security & Audit | Autorizzazioni, tracciabilità, audit trail |

Il progetto è **incrementale**: `Data → Backend → Frontend → Big Data → Security → Cloud → AI`, distribuito su release successive (vedi [Roadmap](#roadmap)).

---

## Stack tecnologico

| Livello | Tecnologia |
|---|---|
| **Backend** | Java + Spring Boot (REST API, documentate con OpenAPI) |
| **Frontend** | Angular (Single Page Application) |
| **Database** | RDBMS SQL relazionale — *versione da definire (in arrivo)* |
| **Versioning** | Git + GitHub (GitHub Flow, Pull Request obbligatorie) |
| **CI/CD** | Pipeline di build/test automatiche (fasi successive) |
| **Containerizzazione** | Docker (fase avanzata) |
| **Evoluzioni previste** | Big Data / streaming per telemetria, sicurezza OAuth2/JWT, deployment cloud, moduli AI con human-in-the-loop |

> ⚠️ La sezione Database verrà aggiornata non appena disponibile la versione utilizzata (RDBMS + versione motore).

---

## Requisiti e versioni

Ambiente di sviluppo Frontend verificato:

| Strumento | Versione |
|---|---|
| Angular CLI | 22.1.8 |
| Node.js | 24.18.0 |
| npm | 11.16.0 |
| Sistema operativo | Windows x64 |

##  Backend

 **GRUPPO ARES**

🔹 **Java Version:** 17
🔹 **Spring Boot Version:** 4.0.0
🔹 **Build Tool:** Maven 3

**Dipendenze:**
- Spring Web MVC
- Spring Data JPA
- Spring Boot Actuator
- H2 Database
- Lombok

## Database: *da aggiungere.*

---

## Avvio rapido

> Sezione da completare man mano che i moduli Backend/Frontend/DB vengono resi avviabili dal repository (obiettivo Sprint 1 — TASK-005, TASK-006).

```bash
# Clonare il repository
git clone <url-repo>
cd aether-platform

# Backend (Spring Boot)
# ...comandi di build/avvio da definire...

# Frontend (Angular)
cd frontend
npm install
ng serve

# Database
# ...script/migrazioni da definire...
```

Criteri di accettazione del cliente per questa sezione:
- il sistema deve essere avviabile **seguendo esclusivamente questa documentazione**;
- lo schema dati deve essere ricreabile da zero tramite migration/script versionati;
- deve esistere un dataset demo sufficiente a dimostrare i casi d'uso principali.

---

## Architettura e domini funzionali

Ogni squad ragiona sull'intera **vertical slice** di un dominio:

```
Requisito → Modello dati → SQL/migrazione → Persistence
          → Service/Business rules → REST API
          → Angular (dove richiesto) → Test → Pull Request → Done
```

Non esistono "reparti" separati per DB/Backend/Frontend/QA: sono ruoli di focus interni alla squad, non proprietà del codice.

---

## Le 5 Squad

| Squad | Dominio principale | Dimensione |
|---|---|---|
| **ARES** | Colonie, habitat, astronauti e competenze | 6 |
| **PHOENIX** | Missioni, equipaggi e pianificazione | 5 |
| **TITAN** | Rover, veicoli, stato e manutenzione | 5 |
| **ORION** | Risorse, stock, movimenti e logistica | 5 |
| **HELIOS** | Incidenti, alert e risposta operativa | 5 |

---

## Organizzazione del lavoro

- **Product Owner / Cliente**: docente — spiega i requisiti, ordina il backlog, accetta/rifiuta in Sprint Review.
- **Scrum Master**: uno studente, a rotazione ad ogni Sprint.
- **Development Team**: 5 squad cross-functional (DB → Backend → API → Frontend → Test → Documentazione).

Regola assenze: nessuna Story deve dipendere da una sola persona.
- almeno 2 persone conoscono il contesto di ogni Story;
- note sempre nella Issue;
- branch remoto sempre aggiornato — niente lavoro tenuto solo in locale.

Il backlog **non deve essere completato tutto**: durante il Planning il team sceglie cosa è realistico chiudere ed impara a dire no al lavoro eccessivo.

---

## Git Workflow

`main` rappresenta sempre una versione integrabile. Nessuno sviluppa direttamente su `main`.

**Naming branch**

```
feature/<issue>-descrizione
fix/<issue>-descrizione
refactor/<issue>-descrizione
docs/<issue>-descrizione
```

Esempi: `feature/31-create-mission`, `fix/44-prevent-double-rover-assignment`, `docs/12-update-er-diagram`

**Convenzione commit**

```
feat: add mission creation endpoint
fix: reject unavailable crew member
test: add resource transfer tests
docs: document mission state machine
refactor: extract validation service
chore: update dependencies
```

**Flusso obbligatorio**

1. Prendere una Issue in `Ready` e assegnarsela.
2. Spostarla in `In Progress`.
3. Creare il branch dedicato.
4. Commit piccoli e comprensibili.
5. Push del branch remoto.
6. Aprire una Pull Request (descrizione con `Closes #<issue>`).
7. Spostare la Story in `Code Review`.
8. Almeno un altro studente esegue la review (l'autore non approva la propria PR).
9. Risolvere i commenti e verificare gli Acceptance Criteria.
10. Merge su `main`.
11. Chiudere la Issue e spostarla in `Done`.

Il reviewer controlla: correttezza, leggibilità, naming, duplicazioni, query SQL, casi limite, gestione NULL, validazioni, error handling, Acceptance Criteria, regressioni.

---

## Scrum Board

| Stage | Significato |
|---|---|
| **Backlog** | Richiesta conosciuta, non ancora pronta |
| **Ready** | Rispetta la Definition of Ready, scelta per lo Sprint |
| **In Progress** | In sviluppo |
| **Code Review** | PR aperta, in attesa di revisione |
| **Testing** | In verifica rispetto agli Acceptance Criteria |
| **Done** | Definition of Done rispettata |

**Limiti WIP**: massimo 2 Story per squadra contemporaneamente in `In Progress`.

**Story Points**: solo 1, 2, 3, 5, 8 — una Story stimata ≥13 deve essere spezzata.

Le Issue non sono pre-assegnate: durante lo Sprint Planning la squadra sceglie la Story, la sposta in `Ready`, crea eventuali Task tecnici e gli studenti si auto-assegnano.

---

## Definition of Ready / Done

**Definition of Ready** — una Story entra nello Sprint quando:
- descrive chiaramente un valore/risultato;
- Acceptance Criteria verificabili;
- dipendenze e dati coinvolti noti;
- è completabile in uno Sprint;
- Story Points stimati.

**Definition of Done** — una Story è Done quando:
- Acceptance Criteria rispettati;
- codice compilabile, query/migrazioni versionate;
- validazioni ed error handling implementati;
- test pertinenti eseguiti;
- PR aperta, almeno una Code Review, commenti risolti;
- merge in `main`;
- documentazione aggiornata se necessaria;
- funzionalità dimostrabile.

---

## Cerimonie Scrum

| Cerimonia | Quando | Durata |
|---|---|---|
| **Daily Stand-up** | Ogni mattina | 10-15 min (max 45s a persona) |
| **Sprint Planning** | Inizio settimana | — |
| **Backlog Refinement** | Metà settimana | 30-45 min |
| **Sprint Review** | Fine settimana | Demo di software funzionante, no slide |
| **Retrospective** | Fine settimana | START / STOP / CONTINUE + 1-2 azioni concrete |

---

## Roadmap

| Sprint | Goal |
|---|---|
| **Sprint 1 — FOUNDATION** | Base tecnica condivisa (E-R v1, schema relazionale, Spring Boot e Angular avviabili, seed data) e una vertical slice minima per dominio |
| **Sprint 2 — CORE OPERATIONS** | Regole di business tra domini, relazioni, validazioni, query con join/aggregazioni, transazioni, componenti Angular |
| **Sprint 3 — INTEGRATED MVP** | Flussi end-to-end, bug fixing, refactoring, test, dashboard, documentazione, demo finale |

Evoluzioni successive previste dal capitolato: Big Data/telemetria a scala, sicurezza avanzata (OAuth2/JWT, OWASP), deployment cloud e CI/CD, moduli AI con supervisione umana.

---

## Documentazione del repository

- `01_PROJECT_AETHER_DOCUMENTO_GENERALE` — capitolato e requisiti completi
- `02_CLASS_OPERATING_MODEL` — modello operativo e ruoli
- `03_BOARD_RULES` — regole della Scrum Board
- `04_GIT_AND_PR_WORKFLOW` — workflow Git e Pull Request
- `05_CEREMONIES` — cerimonie Scrum
- `06_DEFINITION_OF_READY_DONE` — DoR/DoD
- `07_SPRINT_GOALS` — obiettivi dei tre Sprint
- `0X_SPRINT_N_STUDENTI_MISSION_PACK` — backlog e Acceptance Criteria per Sprint
- `aether_backlog.csv` / `aether_backlog.json` — backlog strutturato (Epic/Task/Story per squad e sprint)

---

*README in evoluzione: verrà aggiornato con versione del database, istruzioni di setup complete e dettagli architetturali man mano che il progetto procede.*
