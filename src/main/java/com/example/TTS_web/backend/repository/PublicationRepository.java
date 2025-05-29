package com.example.TTS_web.backend.repository;

import com.example.TTS_web.backend.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query(value = "select * from publication " +
            "where user_id =:userId", nativeQuery = true)
    List<Publication> findAllByUserId(long userId);
}
