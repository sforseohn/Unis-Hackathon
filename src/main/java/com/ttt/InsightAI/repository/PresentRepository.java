package com.ttt.InsightAI.repository;

import com.ttt.InsightAI.domain.DiaryAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentRepository extends JpaRepository<DiaryAnalysis, Long> {
}
