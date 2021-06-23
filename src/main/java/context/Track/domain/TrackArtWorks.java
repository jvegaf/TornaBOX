package context.Track.domain;

import context.Art.domain.Art;

import java.util.ArrayList;
import java.util.List;

public final class TrackArtWorks {

  private final List<Art> value;

  public TrackArtWorks(List<Art> value) {
    this.value = value;
  }

  public TrackArtWorks() {
    this.value = new ArrayList<Art>();
  }

  public List<Art> value(){
    return this.value;
  }
}
