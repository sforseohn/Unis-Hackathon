package com.ttt.InsightAI.controller;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.DiaryAnalysis;
import com.ttt.InsightAI.repository.AnalysisRepository;
import com.ttt.InsightAI.repository.DiaryRepository;
import com.ttt.InsightAI.repository.UserRepository;
import com.ttt.InsightAI.service.OpenAiService;
import com.ttt.InsightAI.service.PresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/present")
public class PresentController {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private PresentService presentService;

    @GetMapping("/{id}")
    public ResponseEntity<DiaryAnalysis> present(@PathVariable Long id) {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Analysis not found"));

        return ResponseEntity.ok(presentService.analyzeAndSave(analysis));
    }
}
