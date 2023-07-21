package com.ttt.InsightAI.repository;

import com.ttt.InsightAI.domain.Present;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentRepository extends JpaRepository<Present, Long> {
}
