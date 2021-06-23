package context.Track.application;

import context.Track.domain.Track;

public abstract class UpdateTrackRequest {
  protected Track previous;
  protected Track changed;
}
