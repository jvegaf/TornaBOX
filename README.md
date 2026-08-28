# TornaBOX

A JavaFX 26 desktop app for managing and editing MP3 metadata — a DJ track
library manager. Built with JDK 25 and Gradle (Kotlin DSL).

## Features

- Browse and manage your in-memory track library (JavaFX `ObservableList`)
- View and edit MP3 tags (artist, title, album, genre, year, BPM, key, artwork)
- Tag tracks from online sources:
  - **Spotify** (official API; needs `.env` credentials)
  - **Beatport** (official v4 catalog API; no credentials required)

## Requirements

- [devenv](https://devenv.sh/) + [direnv](https://direnv.net/), or a working
  Gradle 9 + JDK 25 on your PATH
- `just` for the convenience task runner (optional)

## Setup

```sh
direnv allow                 # activate the devenv shell (Gradle 9, JDK 25)
cp .env_example .env         # set SPOTIFY_ID / SPOTIFY_SECRET (or: just env-setup)
```

There is no Gradle wrapper; use `gradle` from the devenv shell, and run commands
from the repo root so `.env` is found.

## Commands

| Task              | `just` recipe   | `gradle` command                                  |
| ----------------- | --------------- | ------------------------------------------------- |
| Launch the app    | `just run`      | `gradle run`                                      |
| Run tests         | `just test`     | `gradle test`                                     |
| Live Beatport E2E | `just test-live`| `TORNABOX_LIVE_TESTS=true gradle test ...`        |
| Compile           | `just build`    | `gradle build`                                    |
| Native installer  | `just package`  | `gradle package` (jpackage → `fxBuildDemo`)       |
| Clean             | `just clean`    | `gradle clean`                                    |
| Copy `.env`       | `just env-setup`| —                                                 |

`gradle test` is fully offline. The live Beatport integration tests are gated
behind `TORNABOX_LIVE_TESTS=true` because they hit the real API.

## Architecture

`App.java` is the composition root: it constructs every service
(`LibraryService`, `TagService`, `PlayerService`, `MusicFileService`) and passes
them into the controllers, which inject them into views via `injectDeeps(...)`.

The library lives entirely in memory (`ObservableList` in `LibraryService`);
there is no persistence wiring. See `AGENTS.md` for details and known
landmines.
