package com.ttt.InsightAI.controller;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.Present;
import com.ttt.InsightAI.repository.AnalysisRepository;
import com.ttt.InsightAI.repository.PresentRepository;
import com.ttt.InsightAI.service.PresentService;
import com.ttt.InsightAI.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/present")
public class PresentController {

    private final AnalysisRepository analysisRepository;

    private final PresentService presentService;

    @Autowired
    public PresentController(AnalysisRepository analysisRepository, PresentService presentService) {
        this.analysisRepository = analysisRepository;
        this.presentService = presentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Present> present(@PathVariable Long id) {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Analysis not found"));

        return ResponseEntity.ok(presentService.savePresent(analysis));
    }
}
