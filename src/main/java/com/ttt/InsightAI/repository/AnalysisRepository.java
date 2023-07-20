package com.ttt.InsightAI.repository;

import com.ttt.InsightAI.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.User;

import java.util.List;


//GPT 분석 결과(카테고리, 상황분석, 내 감정분석, 상대방 감정분석, 대안, 기대효과, 전체 키워드)
//선물(유튜브, 책)
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    List<Analysis> findByUser(User user);
    Analysis findByDiary(Diary diary);
}
