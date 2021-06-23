package context.Track.domain;

import context.shared.StringValueObject;

import java.time.Year;

public final class TrackYear extends StringValueObject {

  public TrackYear(String value) {
    super(value);
  }

  public TrackYear(Year value) {
    super(value.toString());
  }
}
