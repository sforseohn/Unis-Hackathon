package com.ttt.InsightAI.controller;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.DiaryAnalysis;
import com.ttt.InsightAI.service.PresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/present")
public class PresentController {

    @Autowired
    private PresentService presentService;

    @PostMapping
    public ResponseEntity<DiaryAnalysis> present(@RequestBody Analysis analysis) {
        return ResponseEntity.ok(presentService.analyzeAndSave(analysis));
    }
}
