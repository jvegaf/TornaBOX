package context.Track.domain;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import context.Art.domain.Art;

import java.util.List;

public final class Track {
  private final TrackId id;
  private final TrackArtist artist;
  private final TrackTitle title;
  private final TrackAlbum album;
  private final TrackGenre genre;
  private final TrackYear year;
  private final TrackBPM bpm;
  private final TrackFilePath path;
  private final TrackFileName name;
  private final TrackArtWorks artWorks;
  private final ImageView artImgView;

  public Track(
          TrackId id,
          TrackArtist artist,
          TrackTitle title,
          TrackAlbum album,
          TrackGenre genre,
          TrackYear year,
          TrackBPM bpm,
          TrackFilePath path,
          TrackFileName name,
          TrackArtWorks artList,
          ImageView artImgView) {
    this.id = id;
    this.artist = artist;
    this.title = title;
    this.album = album;
    this.genre = genre;
    this.year = year;
    this.bpm = bpm;
    this.path = path;
    this.name = name;
    this.artWorks = artList;
    this.artImgView = artImgView;
  }

  public static Track fromPrimitives(
          String id,
          String artist,
          String title,
          String album,
          String genre,
          String year,
          Integer bpm,
          String path,
          String name,
          List<Art> arts) {
    return new Track(
            new TrackId(id),
            new TrackArtist(artist),
            new TrackTitle(title),
            new TrackAlbum(album),
            new TrackGenre(genre),
            new TrackYear(year),
            new TrackBPM(bpm),
            new TrackFilePath(path),
            new TrackFileName(name),
            new TrackArtWorks(arts),
            setImgView(arts));
  }

  public static Track fromEmptyValues(String path, String name) {
    return new Track(
            new TrackId(),
            new TrackArtist(""),
            new TrackTitle(""),
            new TrackAlbum(""),
            new TrackGenre(""),
            new TrackYear(""),
            new TrackBPM(null),
            new TrackFilePath(path),
            new TrackFileName(name),
            new TrackArtWorks(),
            setImgView(null));
  }

  private static ImageView setImgView(List<Art> artList) {
    ImageView iv = new ImageView();
    iv.setViewport(new Rectangle2D(0,0,150,28));
    if (artList == null || artList.isEmpty()) {
      Image i = new Image("org/musicbox/assets/art-list-placeholder.png");
      iv.setImage(i);
      return iv;
    }
    iv.setImage(artList.get(0).getArtImage());
    iv.setFitWidth(150);
    iv.setPreserveRatio(true);
    iv.setCache(true);
    return iv;
  }

  public TrackId id() {
    return id;
  }

  public TrackArtist artist() {
    return artist;
  }

  public TrackTitle title() {
    return title;
  }

  public TrackAlbum album() {
    return album;
  }

  public TrackGenre genre() {
    return genre;
  }

  public TrackYear year() {
    return year;
  }

  public TrackBPM bpm() {
    return bpm;
  }

  public TrackFilePath path() {
    return path;
  }

  public TrackFileName name() {
    return name;
  }

  public TrackArtWorks artWorks() { return artWorks; }

  public ImageView artImgView() { return artImgView; }

//  PRIMITIVE GETTERS

  public String getId() {
    return id.value();
  }

  public String getArtist() {
    return artist.value();
  }

  public String getTitle() {
    return title.value();
  }

  public String getAlbum() {
    return album.value();
  }

  public String getGenre() {
    return genre.value();
  }

  public String getYear() {
    return year.value();
  }

  public Integer getBpm() {
    return bpm.value();
  }

  public String getPath() {
    return path.value();
  }

  public String getName() {
    return name.value();
  }

  public List<Art> getArtList() {
    return artWorks.value();
  }

  public ImageView getArtImgView() { return artImgView; }
}
