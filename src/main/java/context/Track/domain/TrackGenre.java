package context.Track.domain;

import context.shared.StringValueObject;

public final class TrackGenre extends StringValueObject {
  public TrackGenre(String value) {
    super(value);
  }

  public TrackGenre() {
    super("");
  }
}
