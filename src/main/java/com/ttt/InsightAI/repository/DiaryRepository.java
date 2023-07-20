package com.ttt.InsightAI.repository;

import com.ttt.InsightAI.domain.Diary;
import com.ttt.InsightAI.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//사용자의 답변(6개 질문에 대한 답 저장)
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUser(User user);
}
