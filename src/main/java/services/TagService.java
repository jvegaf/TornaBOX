package services;

import models.Track;
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
import java.time.Year;
import java.util.List;

public class TagService {

    private File file;
    private AbstractID3v2Tag tag;
    private MP3File f;

    public TagService() {
    }

    public Track createTrackFromFile(File file) {
        this.file = file;
        try {
            f = (MP3File) AudioFileIO.read(file);
            tag = f.getID3v2Tag();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        return generateTrack();
    }

    public Track createTrackFromFilePath(String path) {
        file = new File(path);
        try {
            f = (MP3File) AudioFileIO.read(file);
            tag = f.getID3v2Tag();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        return generateTrack();
    }

    private Track generateTrack() {
        if (!this.f.hasID3v2Tag()) return new Track(this.file.getAbsolutePath());

        return Track.createTrack(
                this.getArtist(),
                this.getTitle(),
                this.getAlbum(),
                this.getGenre(),
                this.getYear(),
                this.getBPM(),
                this.file.getAbsolutePath(),
                this.file.getName()
        );
    }

    private String getTitle() {
        return this.tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
    }

    private String getArtist() {
        return this.tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
    }

    private String getAlbum() {
        return this.tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
    }

    private String getGenre() {
        return this.tag.getFirst(ID3v24Frames.FRAME_ID_GENRE);
    }

    private Year getYear() {
        String _year = this.tag.getFirst(ID3v24Frames.FRAME_ID_YEAR);
        if (_year != null && !_year.equals("") && _year.length() == 4) {
            return Year.parse(_year);
        }
        return null;
    }

    private Integer getBPM() {
        String _bpm = this.tag.getFirst(ID3v24Frames.FRAME_ID_BPM);
        if (_bpm != null && !_bpm.equals("")) {
            return Integer.parseInt(_bpm);
        }
        return null;
    }

    private byte[] getCoverData() {
        List<Artwork> artworkList = this.tag.getArtworkList();
        if (artworkList.isEmpty()) return new byte[0];
        Artwork artwork = this.tag.getFirstArtwork();
        return artwork.getBinaryData();
    }
}
