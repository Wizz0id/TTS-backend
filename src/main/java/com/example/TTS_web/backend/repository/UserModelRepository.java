package com.example.TTS_web.backend.repository;

import com.example.TTS_web.backend.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long> {
    @Query(value = "select * from user_model " +
            "where user_id=:userId", nativeQuery = true)
    List<UserModel> findByUserId(long userId);
}
