package model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MkbTableColumn {
    String column;
    String value;
}
