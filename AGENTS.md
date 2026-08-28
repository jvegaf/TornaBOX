# AGENTS.md

JavaFX 26 desktop app for managing/editing MP3 metadata (a DJ track library manager). JDK 25, Gradle Kotlin DSL, devenv (Nix) + direnv.

## Commands
- **No Gradle wrapper** — `gradlew` does not exist and is gitignored. Use `gradle` (Gradle 9, provided by devenv via `.envrc`).
- `gradle run` — launch the app. Must run from repo root (see env note).
- `gradle test` — runs tests (JUnit 5).
- `gradle build` — compile.
- `gradle package` — custom jpackage task producing a native installer (currently named the placeholder `fxBuildDemo`).

## Environment secrets
- `SpotifyTagger`'s constructor calls `Dotenv.load()`, which reads `.env` from the **working directory**. It needs `SPOTIFY_ID` and `SPOTIFY_SECRET` or it fails with an uncaught exception at startup.
- Copy `.env_example` → `.env` and set real credentials. `.env` is gitignored.
- Run Gradle from the repo root or creds won't load.

## Architecture
- `App.java` is the composition root: it constructs every service (`LibraryService`, `TagService`, `PlayerService`, `MusicFileService`) and passes `this` (the `App`) into the controllers. Services are injected into views via `injectDeeps(...)`.
- To add a new service, create it in `App.initServices()`, add a getter, and pass it through `MainViewController` → components. Don't construct shared services inside views.
- `LibraryService` holds tracks in a JavaFX `ObservableList` — the library is entirely **in-memory**; there is no persistence wiring.

## Data layer
- `etc/databases/tb_database.sql` is the intended schema (songs/playlists, MySQL). It is **not wired into the app** — no JDBC/DB driver or connection code exists anywhere in `src/`. Treat it as a design artifact, not a live store. Note: it defines the `genre` column twice (a bug) and lacks columns the `Track` model uses (`key`, artwork, etc.).

## Taggers
- `services/tagger/`:
  - `SpotifyTagger` — official Spotify API using env credentials. `.env` required.
  - `BeatportTagger` — htmlunit scraping of beatport.com plus an unofficial `embed.beatport.com/token` endpoint. Fragile and subject to breaking when markup changes.
  - `Tagger.java` is an **empty placeholder class**, not an abstraction/interface. Don't rely on it.
- Search-result flow targets `TagDTO` / `SearchResult` DTOs under the same package.

## Known landmines (don't assume they're real features)
- `jMusixMatch` is declared in `build.gradle.kts` but never used.
- `BeatportTagger.fetchTrackEmbed` only prints JSON to stdout — WIP, not a functional feature yet.
- Multiple CSS themes under `resources/styles/` are unused; only `dark.css` is loaded (`App.java`).
- `src/test/java/me/jvegaf/tornabox/services/SpotifyTaggerTest.java.bak` is stale backup, not a live test.

## Notes
- CSSFX hot-reloads CSS during `gradle run` (started in `App.start`), so CSS edits apply live in dev.
- `README.md` is currently empty. There is no CI, formatter, or linter config.
