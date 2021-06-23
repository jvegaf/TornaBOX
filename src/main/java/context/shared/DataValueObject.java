package context.shared;

public abstract class DataValueObject {
  private byte[] value;

  public DataValueObject(byte[] value) {
    this.value = value;
  }

  public byte[] data() {
    return this.value;
  }
}
