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
| **Database** | PostgreSQL (istanza gestita Supabase) |
| **Versioning** | Git + GitHub (GitHub Flow, Pull Request obbligatorie) |
| **CI/CD** | GitHub Actions — build/test automatiche di backend e frontend |
| **Containerizzazione** | Docker (fase avanzata) |
| **Evoluzioni previste** | Big Data / streaming per telemetria, sicurezza OAuth2/JWT, deployment cloud, moduli AI con human-in-the-loop |

---

## Requisiti e versioni

### Frontend

| Strumento | Versione |
|---|---|
| Angular | 22.1.x |
| Angular CLI | 22.1.8 |
| TypeScript | ~6.0.2 |
| Node.js | ≥ 22 (in CI: 22) |
| npm | 11.12.1 |
| Sistema operativo | multipiattaforma (Windows / macOS) |

### Backend — GRUPPO ARES

| Strumento | Versione |
|---|---|
| Java | 25 (Temurin in CI) |
| Spring Boot | 3.5.16 |
| Maven | 3.9.16 (via wrapper `./mvnw`) |

**Dipendenze principali** (`backend/pom.xml`):
- Spring Web MVC (REST API)
- Spring Data JPA
- Spring Boot Validation
- Spring Boot Actuator
- springdoc-openapi 2.6.0 (documentazione OpenAPI / Swagger UI)
- PostgreSQL Driver (runtime)
- H2 (runtime)
- Lombok 1.18.48

### Database

| Strumento | Versione |
|---|---|
| PostgreSQL | istanza gestita **Supabase** (pooler `aws-1-eu-west-1`, porta 6543 transaction mode, SSL richiesto) |

- Credenziali lette dalle variabili d'ambiente `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` (vedi `backend/src/main/resources/application.yml`)
- Schema e seed gestiti da **Flyway**: `backend/src/main/resources/db/migration/V1__schema.sql` + `V2__seed.sql` — il DB si ricostruisce da zero avviando il backend, senza script manuali

---

## Avvio rapido

```bash
# Clonare il repository
git clone <url-repo>
cd aether-platform

# Backend (Spring Boot — richiede Java 25)
cd backend
./mvnw spring-boot:run
# API su http://localhost:8080 — Swagger UI su /swagger-ui/index.html

# Frontend (Angular — richiede Node.js >= 22)
cd frontend
npm install
npm start
# App su http://localhost:4200

# Database
# Lo schema viene creato/aggiornato automaticamente da Hibernate all'avvio del backend
# (ddl-auto: update, configurato in backend/src/main/resources/application.yml)
# Lo schema SQL completo di progetto e il dataset demo sono documentati in docs/sql/
# Le credenziali vanno fornite tramite DB_URL / DB_USERNAME / DB_PASSWORD
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

### `docs/agile/`
- `CEREMONIES.md` — cerimonie Scrum
- `DEFINITION_OF_READY_DONE.md` — DoR/DoD
- `GIT_WORKFLOW.md` — workflow Git e Pull Request

### `docs/database/`
- `AETHER_Modello_ER.md` / `DB_Modello_ER.docx` — modello E-R e schema logico
- `05_AETHER_seed.sql` — dataset demo (seed)

### `docs/onboarding/`
- `DAY1_STUDENT_KICKOFF.md` — kick-off studenti

### Sprint
- `docs/sprint-1/README.md` — goal e backlog Sprint 1
- `docs/sprint-2/README.md` — goal e backlog Sprint 2
- `docs/sprint-3/AETHER_Sprint3_Student_Mission_Pack.docx` — mission pack Sprint 3

---

*README in evoluzione: verrà aggiornato con istruzioni di setup complete e dettagli architetturali man mano che il progetto procede.*
