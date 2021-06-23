package context.Track.domain;

import context.shared.Uuid;

public final class TrackId extends Uuid {

  public TrackId() { super(); }

  public TrackId(String value) {
    super(value);
  }
}
