package context.shared;

import java.time.Year;

public abstract class YearValueObject {
  private Year value;

  public YearValueObject(String value) {
    this.value = Year.parse(value);
  }

  public YearValueObject(Year value) {
    this.value = value;
  }
}
