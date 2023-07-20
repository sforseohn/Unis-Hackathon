package com.ttt.InsightAI.service;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.DiaryAnalysis;
import com.ttt.InsightAI.repository.PresentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentService {

    @Autowired
    private PresentRepository presentRepository;

    @Autowired
    private YoutubeService youtubeService;

    public DiaryAnalysis analyzeAndSave(Analysis analysis) {
        // Here you would need to get keywords somehow
        List<String> keywords = analysis.getKeywords();
        List<String> youtubeUrls = youtubeService.searchVideos(keywords);
        DiaryAnalysis diaryAnalysis = new DiaryAnalysis();
        diaryAnalysis.setUser(analysis.getUser());
        diaryAnalysis.setDiary(analysis.getDiary());
        diaryAnalysis.setYoutubeUrl(youtubeUrls);

        try{
            diaryAnalysis = presentRepository.save(diaryAnalysis);
            return diaryAnalysis;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error scheduling tasks: " + e.getMessage());
        }

        return diaryAnalysis;
    }
}
