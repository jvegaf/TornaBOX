package context.Track.domain;

import context.shared.StringValueObject;

public final class TrackTitle extends StringValueObject {
  public TrackTitle(String value) {
    super(value);
  }

  public TrackTitle() {
    super("");
  }
}
