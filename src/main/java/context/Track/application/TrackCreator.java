package context.Track.application;

import context.Track.domain.TrackRepository;

public final class TrackCreator {
  private TrackRepository repository;

  public TrackCreator(TrackRepository repository){
    this.repository = repository;
  }
}
