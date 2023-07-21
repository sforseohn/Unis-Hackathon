package com.ttt.InsightAI.service;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.Present;
import com.ttt.InsightAI.repository.AnalysisRepository;
import com.ttt.InsightAI.repository.DiaryRepository;
import com.ttt.InsightAI.repository.PresentRepository;
import com.ttt.InsightAI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentService {

    private final PresentRepository presentRepository;

    private final YoutubeService youtubeService;

    @Autowired
    public PresentService(PresentRepository presentRepository, YoutubeService youtubeService) {
        this.presentRepository = presentRepository;
        this.youtubeService = youtubeService;
    }

    public Present savePresent(Analysis analysis) {
        // Here you would need to get keywords somehow
        List<String> keywords = analysis.getKeywords();
        List<String> youtubeUrls = youtubeService.searchVideos(keywords);
        Present present = new Present();
        present.setUser(analysis.getUser());
        present.setDiary(analysis.getDiary());
        present.setYoutubeUrl(youtubeUrls);

        present = presentRepository.save(present);

        try{
            System.out.println("선물 저장 완료!");
            return present;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error scheduling tasks: " + e.getMessage());
        }

        return present;
    }
}
