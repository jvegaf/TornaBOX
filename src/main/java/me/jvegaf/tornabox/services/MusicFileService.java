package me.jvegaf.tornabox.services;

import me.jvegaf.tornabox.models.Track;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MusicFileService {
  private final TagService tagService;

  public MusicFileService(TagService tagService) {
    this.tagService = tagService;
  }

  public ArrayList<Track> processMusicFilesOfPath(File path) {
    List<Track> files = getFilesOfPath(path).stream().map((Path file) -> this.tagService.createTrackFromFile(file.toFile())).toList();
    if (files.isEmpty()) {
      return new ArrayList<>();
    }
    return new ArrayList<>(files);
  }

  private List<Path> getFilesOfPath(File path) {
    try {
      return Files.walk(Paths.get(path.getPath())).filter(file -> file.toString().endsWith(".mp3")).collect(toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
