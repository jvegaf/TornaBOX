package context.shared;

public abstract class BooleanValueObject {
  private boolean value;

  public BooleanValueObject(boolean value) {
    this.value = value;
  }

  public boolean value() {
    return this.value;
  }
}
