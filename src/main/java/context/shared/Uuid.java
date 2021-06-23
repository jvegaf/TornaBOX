package context.shared;

import java.util.UUID;

public abstract class Uuid extends Identifier {

  public Uuid() {
    super(UUID.randomUUID().toString());
  }

  public Uuid(String value) {
    super(value);
  }
}
