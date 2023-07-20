package com.ttt.InsightAI.controller;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.Diary;
import com.ttt.InsightAI.domain.DiaryAnalysisRequest;
import com.ttt.InsightAI.domain.User;
import com.ttt.InsightAI.repository.AnalysisRepository;
import com.ttt.InsightAI.repository.DiaryRepository;
import com.ttt.InsightAI.repository.UserRepository;
import com.ttt.InsightAI.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/diary-analysis")
public class DiaryAnalysisController {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final AnalysisRepository analysisRepository;
    private final OpenAiService openAiService;

    @Autowired
    public DiaryAnalysisController(UserRepository userRepository, DiaryRepository diaryRepository, AnalysisRepository analysisRepository, OpenAiService openAiService) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        this.analysisRepository = analysisRepository;
        this.openAiService = openAiService;
    }

    @PostMapping
    public Analysis analyzeDiary(@RequestBody Diary request) {
        if (request.getId() == null || request.getAnswer1() == null || request.getAnswer2() == null || request.getAnswer3() == null ||
                request.getAnswer4() == null || request.getAnswer5() == null ||request.getAnswer6() == null ||
                request.getDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is invalid");
        }

        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Diary diary = new Diary();
        diary.setUser(user);
        diary.setAnswer1(request.getAnswer1());
        diary.setAnswer2(request.getAnswer2());
        diary.setAnswer3(request.getAnswer3());
        diary.setAnswer4(request.getAnswer4());
        diary.setAnswer5(request.getAnswer5());
        diary.setAnswer6(request.getAnswer6());
        diary.setDate(request.getDate());

        diary = diaryRepository.save(diary); // This will save the diary

        Analysis analysis = new Analysis();
        analysis.setUser(user);
        analysis.setDiary(diary); // Now we can set the Diary to the Analysis

        try {
            analysis = openAiService.fetchDiaryAnalysis(diary, analysis);
            analysis = analysisRepository.save(analysis); // This will save the analysis
            diary.setAnalysis(analysis);  // Set the Analysis to the Diary after saving the analysis
            return analysis;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error scheduling tasks: " + e.getMessage());
        }

        analysis = analysisRepository.save(analysis); // This will save the analysis

        diary.setAnalysis(analysis);  // Set the Analysis to the Diary after saving the analysis

        return analysis;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Analysis>> getAnalysis(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(analysisRepository.findByUser(user));
    }
}

