package model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MkbEntity {
    String disease;
    String code;
    boolean clinRecAvailability = false;
}
