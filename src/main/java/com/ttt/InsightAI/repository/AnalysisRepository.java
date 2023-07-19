package com.ttt.InsightAI.repository;

import com.ttt.InsightAI.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.User;

import java.util.List;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    List<Analysis> findByUser(User user);
    Analysis findByDiary(Diary diary);
}
