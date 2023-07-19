package com.ttt.InsightAI.repository;

import com.ttt.InsightAI.domain.Diary;
import com.ttt.InsightAI.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUser(User user);
}
