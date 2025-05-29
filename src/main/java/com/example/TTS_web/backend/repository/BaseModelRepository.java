package com.example.TTS_web.backend.repository;

import com.example.TTS_web.backend.entity.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseModelRepository extends JpaRepository<BaseModel, Long> {
    @Query(value = "select * from base_model " +
            "where name ilike :name ", nativeQuery = true)
    Optional<BaseModel> findByName(String name);
}
