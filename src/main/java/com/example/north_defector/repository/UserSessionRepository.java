package com.example.north_defector.repository;

import com.example.north_defector.domain.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    void deleteAllByUserNo(Integer userNo);

    UserSession findByUser_Email(String email);
    UserSession findByUserNo(Integer userNo);
}
