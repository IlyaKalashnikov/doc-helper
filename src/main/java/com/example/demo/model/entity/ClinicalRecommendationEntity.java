package com.example.demo.model.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonType.class)
@Table(name = "clinrec")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalRecommendationEntity {
    @Id
    String id;
    @Column(length = 1000)
    String name;
    @Type(type = "jsonb")
    @Column(
            name = "mkb",
            columnDefinition = "jsonb"
    )
    ArrayList<String> mkb;
    @Column(name = "publishdate")
    Date publishDate;
    @Column(length = 1000)
    String author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClinicalRecommendationEntity that = (ClinicalRecommendationEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
