package com.example.dochelper.repository.mkb_repository;

import com.example.dochelper.model.entity.MkbEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MkbRepository extends CrudRepository<MkbEntity, String> {
    @Query("from MkbEntity e where lower (e.disease) LIKE lower (concat('%', :disease, '%'))")
    List<MkbEntity> findMkbByDisease (@Param(value = "disease") String disease);
}
