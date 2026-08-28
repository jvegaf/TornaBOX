# AGENTS.md

JavaFX 26 desktop app for managing/editing MP3 metadata (a DJ track library manager). JDK 25, Gradle Kotlin DSL, devenv (Nix) + direnv.

## Commands
- **No Gradle wrapper** — `gradlew` does not exist and is gitignored. Use `gradle` (Gradle 9, provided by devenv via `.envrc`).
- A `justfile` wraps the common recipes (run/test/build/package/clean/env-setup); see `just --list`. Requires `just` on PATH (pure convenience — everything below also runs directly via `gradle`).
- `gradle run` — launch the app. Must run from repo root (see env note).
- `gradle test` — runs tests (JUnit 5). Fully **offline**.
- `TORNABOX_LIVE_TESTS=true gradle test --tests '...BeatportTaggerTest'` — also runs live Beatport integration tests (hits the real API). Equivalently `just test-live`.
- `gradle build` — compile.
- `gradle package` — custom jpackage task producing a native installer (currently named the placeholder `fxBuildDemo`).
- `gradle clean` — clean build artifacts.

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
  - `BeatportTagger` — official Beatport v4 catalog API (OAuth client-credentials token from `account.beatport.com/o/token/`, search via `/v4/catalog/search?type=tracks`, track via `/v4/catalog/tracks/{id}`). No `.env` needed (creds are hardcoded). JSON parsing/mapping is kept static for offline testing against `doc/response*.json` fixtures.
  - `Tagger.java` is an **empty placeholder class**, not an abstraction/interface. Don't rely on it.
- Search results are mapped into `TagDTO` (with a `BeatportTrack` Gson model under the same package). Note `TagDTO.getImages()` is currently unread by the views — the detail view shows artwork read from the local MP3 via `Track.getArtworkData()`.

## Known landmines (don't assume they're real features)
- `jMusixMatch` is declared in `build.gradle.kts` but never used.
- Multiple CSS themes under `resources/styles/` are unused; only `dark.css` is loaded (`App.java`).
- `src/test/java/me/jvegaf/tornabox/services/SpotifyTaggerTest.java.bak` is stale backup, not a live test.

## Notes
- CSSFX hot-reloads CSS during `gradle run` (started in `App.start`), so CSS edits apply live in dev.
- There is no CI, formatter, or linter config.
