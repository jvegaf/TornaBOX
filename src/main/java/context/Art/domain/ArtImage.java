package context.Art.domain;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public final class ArtImage {

  private final Image artImage;

  public ArtImage(byte[] data) {
    this.artImage = new Image(new ByteArrayInputStream(data));
  }

  public Image value(){
    return this.artImage;
  }
}
