package context.Track.domain;

import context.shared.StringValueObject;

public final class TrackFileName extends StringValueObject {
  public TrackFileName(String value) {
    super(value);
  }

  public TrackFileName() {
    super("");
  }
}
