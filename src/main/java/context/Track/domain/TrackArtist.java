package context.Track.domain;

import context.shared.StringValueObject;

public final class TrackArtist extends StringValueObject {
  public TrackArtist(String value) {
    super(value);
  }

  public TrackArtist() {
    super("");
  }
}
