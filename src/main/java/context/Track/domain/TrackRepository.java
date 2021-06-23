package context.Track.domain;

import java.util.Optional;

public interface TrackRepository {
  void save(Track track);
  Optional<Track> search(TrackId id);
}
