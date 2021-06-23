package context.Art.domain;

import javafx.scene.image.Image;

public final class Art {
  private final ArtData data;
  private final ArtDescription description;
  private final ArtImageUrl imageUrl;
  private final ArtIsLinked linked;
  private final ArtMimeType mimeType;
  private final ArtPictureType pictureType;
  private final ArtImage artImage;

  public Art(
          ArtData data,
          ArtDescription description,
          ArtImageUrl imageUrl,
          ArtIsLinked linked,
          ArtMimeType mimeType,
          ArtPictureType pictureType,
          ArtImage artImage)
  {
    this.data = data;
    this.description = description;
    this.imageUrl = imageUrl;
    this.linked = linked;
    this.mimeType = mimeType;
    this.pictureType = pictureType;
    this.artImage = artImage;
  }

  public static Art fromPrimitives (
          byte [] data,
          String description,
          String imageUrl,
          boolean linked,
          String mimeType,
          int pictureType
  ) {
    return new Art(
            new ArtData(data),
            new ArtDescription(description),
            new ArtImageUrl(imageUrl),
            new ArtIsLinked(linked),
            new ArtMimeType(mimeType),
            new ArtPictureType(pictureType),
            new ArtImage(data));
  }

  public ArtData data() {
    return data;
  }

  public ArtDescription description() {
    return description;
  }

  public ArtImageUrl imageUrl() {
    return imageUrl;
  }

  public ArtIsLinked linked() {
    return linked;
  }

  public ArtMimeType mimeType() {
    return mimeType;
  }

  public ArtPictureType pictureType() {
    return pictureType;
  }

  private ArtImage artImage() {
    return artImage;
  }

  /* Primitives Getters */
  public byte[] getData() {
    return data.data();
  }

  public String getDescription() {
    return description.value();
  }

  public String getImageUrl() {
    return imageUrl.value();
  }

  public boolean getLinked() {
    return linked.value();
  }

  public String getMimeType() {
    return mimeType.value();
  }

  public Integer getPictureType() {
    return pictureType.value();
  }

  public Image getArtImage() { return artImage.value(); }

}
