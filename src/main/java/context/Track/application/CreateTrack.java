package context.Track.application;

import context.Art.domain.Art;
import context.Track.domain.Track;
import context.Track.domain.TrackId;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CreateTrack {

  public static Track run(File file) {
    try {
      MP3File f = (MP3File) AudioFileIO.read(file);
      AbstractID3v2Tag tag = f.getID3v2Tag();
      if (tag == null) return Track.fromEmptyValues(file.getAbsolutePath(), file.getName());
      return Track.fromPrimitives(
              new TrackId().value(),
              getArtist(tag),
              getTitle(tag),
              getAlbum(tag),
              getGenre(tag),
              getYear(tag),
              getBPM(tag),
              file.getAbsolutePath(),
              file.getName(),
              getArtWorks(tag)
      );
    } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
            | InvalidAudioFrameException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static String getTitle(AbstractID3v2Tag tag) {
    if (tag.getFirst(ID3v24Frames.FRAME_ID_TITLE) == null) return "";
    return tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
  }

  private static String getArtist(AbstractID3v2Tag tag) {
    if (tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST) == null) return "";
    return tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
  }

  private static String getAlbum(AbstractID3v2Tag tag) {
    if (tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM) == null) return "";
    return tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
  }

  private static String getGenre(AbstractID3v2Tag tag) {
    if (tag.getFirst(ID3v24Frames.FRAME_ID_GENRE) == null) return "";
    return tag.getFirst(ID3v24Frames.FRAME_ID_GENRE);
  }

  private static String getYear(AbstractID3v2Tag tag) {
    String _year = tag.getFirst(ID3v24Frames.FRAME_ID_YEAR);
//    if (_year != null && !_year.equals("") && _year.length() == 4) {
//      return Year.parse(_year);
//    }
    return _year;
  }

  private static Integer getBPM(AbstractID3v2Tag tag) {
    String _bpm = tag.getFirst(ID3v24Frames.FRAME_ID_BPM);
    if (_bpm != null && !_bpm.equals("")) {
      try {
        return Integer.parseInt(_bpm);
      } catch (Exception e){
        return null;
      }
    }
    return null;
  }

  private static List<Art> getArtWorks(AbstractID3v2Tag tag) {
    ArrayList<Art> artWorks = new ArrayList<>();
    List<Artwork> artworkList = tag.getArtworkList();
    if (artworkList.isEmpty()) return artWorks;
    System.out.println("");
    System.out.printf("ARTWORK OF %s \n", tag.getFirst(ID3v24Frames.FRAME_ID_TITLE));
    for (Artwork artwork : artworkList) {
      System.out.printf("imgUrl: %s, mimeType: %s, pictureType: %s \n", artwork.getImageUrl(),artwork.getMimeType(),artwork.getPictureType());
      artWorks.add(Art.fromPrimitives(
              artwork.getBinaryData(),
              artwork.getDescription(),
              artwork.getImageUrl(),
              artwork.isLinked(),
              artwork.getMimeType(),
              artwork.getPictureType()
      ));
    }
    return artWorks;
  }
}
