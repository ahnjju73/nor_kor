package com.example.north_defector.repository;

import com.example.north_defector.domain.UserPw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPwRepository extends JpaRepository<UserPw, Integer> {
    UserPw findByUser_Email(String email);

}
