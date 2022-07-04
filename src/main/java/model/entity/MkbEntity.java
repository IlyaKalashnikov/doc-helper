package model.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.Objects;

@Entity
@Table(name = "Codification")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MkbEntity {
    @Id
    String code;
    @Column(length = 400)
    String disease;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MkbEntity mkbEntity = (MkbEntity) o;
        return code != null && Objects.equals(code, mkbEntity.code);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
