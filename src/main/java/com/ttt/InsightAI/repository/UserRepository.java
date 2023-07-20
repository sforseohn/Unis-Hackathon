package com.ttt.InsightAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ttt.InsightAI.domain.User;

//사용자(별명, 비밀번호)
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
