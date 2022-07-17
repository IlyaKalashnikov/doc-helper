package com.example.demo.dochelper.model.entity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "mkb")
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
