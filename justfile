# TornaBOX — JavaFX MP3 metadata manager task runner
#
# Requires `just` and the devenv shell (`direnv allow` at repo root) so that
# `gradle` (Gradle 9) is on PATH. There is no Gradle wrapper.

set shell := ["bash", "-eu", "-c"]

# Show this help
default:
    @just --list

# Launch the app
run:
    gradle run

# Run the test suite (JUnit 5, no network)
test:
    gradle test

# Run live Beatport integration tests (hits the real API)
test-live:
    TORNABOX_LIVE_TESTS=true gradle test --tests 'me.jvegaf.tornabox.services.tagger.BeatportTaggerTest'

# Compile the project
build:
    gradle build

# Build a native installer with jpackage (currently named fxBuildDemo)
package:
    gradle package

# Clean build artifacts
clean:
    gradle clean
