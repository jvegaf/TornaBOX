# AGENTS.md

JavaFX 26 desktop app for managing/editing MP3 metadata (a DJ track library manager). JDK 25, Gradle Kotlin DSL, devenv (Nix) + direnv.

## Commands
- **No Gradle wrapper** — `gradlew` does not exist and is gitignored. Use `gradle` (Gradle 9, provided by devenv via `.envrc`).
- A `justfile` wraps the common recipes (run/test/build/package/clean); see `just --list`. Requires `just` on PATH (pure convenience — everything below also runs directly via `gradle`).
- `gradle run` — launch the app. Must run from repo root (see env note).
- `gradle test` — runs tests (JUnit 5). Fully **offline**.
- `TORNABOX_LIVE_TESTS=true gradle test --tests '...BeatportTaggerTest'` — also runs live Beatport integration tests (hits the real API). Equivalently `just test-live`.
- `gradle build` — compile.
- `gradle package` — custom jpackage task producing a native installer (currently named the placeholder `fxBuildDemo`).
- `gradle clean` — clean build artifacts.

## Architecture
- `App.java` is the composition root: it constructs every service (`LibraryService`, `TagService`, `PlayerService`, `MusicFileService`) and passes `this` (the `App`) into the controllers. Services are injected into views via `injectDeeps(...)`.
- To add a new service, create it in `App.initServices()`, add a getter, and pass it through `MainViewController` → components. Don't construct shared services inside views.
- `LibraryService` holds tracks in a JavaFX `ObservableList` — the library is entirely **in-memory**; there is no persistence wiring.

## Data layer
- `etc/databases/tb_database.sql` is the intended schema (songs/playlists, MySQL). It is **not wired into the app** — no JDBC/DB driver or connection code exists anywhere in `src/`. Treat it as a design artifact, not a live store. Note: it defines the `genre` column twice (a bug) and lacks columns the `Track` model uses (`key`, artwork, etc.).

## Taggers
- `services/tagger/`:
  - `BeatportTagger` — official Beatport v4 catalog API (OAuth client-credentials token from `account.beatport.com/o/token/`, search via `/v4/catalog/search?type=tracks`, track via `/v4/catalog/tracks/{id}`). No `.env` needed (creds are hardcoded). JSON parsing/mapping is kept static for offline testing against `doc/response*.json` fixtures.
  - `Tagger.java` is an **empty placeholder class**, not an abstraction/interface. Don't rely on it.
- Search results are mapped into `TagDTO` (with a `BeatportTrack` Gson model under the same package). Artwork images are plain `models.Image` beans (immutable `url`/`width`/`height`). Note `TagDTO.getImages()` is currently unread by the views — the detail view shows artwork read from the local MP3 via `Track.getArtworkData()`.

## Known landmines (don't assume they're real features)
- `jMusixMatch` is declared in `build.gradle.kts` but never used.
- Multiple CSS themes under `resources/styles/` are unused; only `dark.css` is loaded (`App.java`).

## Notes
- CSSFX hot-reloads CSS during `gradle run` (started in `App.start`), so CSS edits apply live in dev.
- There is no CI, formatter, or linter config.

## Native libs (NixOS) — load-bearing
- JavaFX extracts its native binaries (`glassgtk3`, `prism_es2`, ...) to `~/.openjfx/cache/<ver>/amd64/` and loads them with `System.load()` — **no RPATH** into the Nix store. `devenv.nix` therefore exports `env.LD_LIBRARY_PATH` (gtk3, glib, pango, atk, cairo, gdk-pixbuf, X11/Xtst/Xxf86vm, libGL, fontconfig, freetype). Removing it breaks `gradle run` with `UnsatisfiedLinkError` on NixOS.
- `applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics")` in `build.gradle.kts` silences a cosmetic restricted-method warning from JavaFX 25+ native loading; keep it.
- Sanity check after touching `devenv.nix`: `ldd ~/.openjfx/cache/*/amd64/*.so | grep -c "not found"` must print `0`.
