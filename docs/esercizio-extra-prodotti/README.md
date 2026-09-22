# Esercizio extra — catalogo prodotti/categorie

Esercizio di allenamento, stessa difficoltà e stesse trappole di
`Prof 2/esercizio_microservizi.txt` (continenti/nazioni), ma con dominio diverso
per non ripetere a memoria la stessa soluzione. La traccia completa è in
`traccia.txt`, da leggere per prima.

Il file dati (`dati/catalogo_prodotti.json`, 108 voci) è generato appositamente per
questo esercizio: nessun dato reale, prodotti e prezzi fittizi. È sicuro da
pubblicare così com'è.

## Perché serve caricarlo su GitHub

Il progetto va costruito come client HTTP che scarica il JSON da un URL esterno,
esattamente come l'esercizio originale scarica `countries.json` da
raw.githubusercontent.com. Per riprodurre lo stesso pattern serve un URL raw
pubblico, quindi il file va ospitato su un repository GitHub.

## Step da seguire

### 1. Pubblicare il file JSON

1. Crea (o riusa) un repository GitHub pubblico.
2. Carica `dati/catalogo_prodotti.json` in quel repository (va bene anche nella
   root, o in una cartella tipo `dist/`).
3. Prendi l'URL "raw" del file: sulla pagina del file su github.com, pulsante
   "Raw" → copia l'URL (avrà la forma
   `https://raw.githubusercontent.com/<utente>/<repo>/refs/heads/main/<percorso>/catalogo_prodotti.json`).
4. Verifica che l'URL funzioni con una richiesta semplice, es. da PowerShell:
   `Invoke-WebRequest <url-raw> | Select-Object -ExpandProperty Content`
   — deve restituire il JSON.

### 2. Creare il progetto

Nuovo modulo Maven indipendente, sullo stesso pattern di
`continenti-nazioni-service/`: Spring Initializr, Java 21, Spring Boot 4.1.1,
dependencies Web, Data JPA, Validation, Lombok, PostgreSQL Driver — poi aggiungere
a mano Flyway, RestClient (o OpenFeign), springdoc-openapi come già fatto per
continenti-nazioni-service (vedi `docs/ESERCIZI_ESAME.md`, sezione S0, per le
coordinate esatte delle dipendenze).

Nome suggerito: `catalogo-prodotti-service/`, cartella indipendente nella root del
repository (non dentro `Prof 2/`).

### 3. Piano di costruzione (stessa sequenza S0–S11 dell'esercizio principale)

| Step | Contenuto |
|---|---|
| S0 | Spring Initializr + dipendenze aggiuntive (Flyway, RestClient/Feign, springdoc) |
| S1 | Struttura a 3 strati (`domain`, `repository`, `service`, `web`, `client`) + entity `Categoria`/`Prodotto` |
| S2 | Migrazioni Flyway: V1 categorie (+ insert default, incluso "Non Categorizzato"), V2 prodotti (FK, UNIQUE su sku) |
| S3 | Repository Spring Data JPA (query derivata "prodotti per categoria") |
| S4 | DTO di confine `ProdottoJson` + client HTTP verso l'URL raw ottenuto allo step 1 — attenzione alle 2 trappole descritte in `traccia.txt` (radice oggetto, campo sconosciuto) |
| S5 | `ImporterService`: derivazione categoria dal prefisso di `sku`, esclusione sku non mappabili (eccetto "PROMO-OMAGGIO", default), idempotenza sul vincolo DB |
| S6 | DTO di risposta + mapper |
| S7 | Controller REST: `POST /import`, `GET /categorie`, `GET /categorie/{id}`, `GET /prodotti`, `GET /prodotti/{id}`, `GET /categorie/{id}/prodotti` |
| S8 | Validazione + `@RestControllerAdvice` + `ProblemDetail` (400/404/409) |
| S9 | OpenAPI/Swagger |
| S10 | Dockerfile multi-stage + `.dockerignore` + docker-compose (postgres + servizio, con healthcheck) |
| S11 | Push su GitHub |

Procedi uno step alla volta e verifica ogni step (compilazione, avvio, curl) prima
di passare al successivo — stesso approccio già seguito per
`continenti-nazioni-service/`.
