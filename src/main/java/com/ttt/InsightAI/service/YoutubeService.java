package com.ttt.InsightAI.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeService {
    private static final String APPLICATION_NAME = "AIzaSyAGl4TZ9ldm4oLG5OnJbmTdmP2aLsG_yH0";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    String searchWord="고민상담 인간관계";

    public List<String> searchVideos(List<String> keywords) {
        List<String> youtubeUrls = new ArrayList<>();

        try {
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            YouTube youtubeService = new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // Define the API request for retrieving search results.
            YouTube.Search.List request = youtubeService.search()
                    .list("id,snippet");

            //keywords loop를 돌면서 하나의 검색어로 만들어서 작성
            for(String keyword : keywords){
                searchWord += " "+keyword;
            }
            System.out.print("검색어는 다음과 같습니다.: ");
            System.out.println(searchWord);

            SearchListResponse response = request.setMaxResults(4L)//4개 보여주기
                    .setQ(searchWord)
                    .setType("video")
                    .setKey("AIzaSyAGl4TZ9ldm4oLG5OnJbmTdmP2aLsG_yH0")
                    .execute();

            for (SearchResult searchResult : response.getItems()) {
                String videoId = searchResult.getId().getVideoId();
                youtubeUrls.add("https://www.youtube.com/watch?v=" + videoId);
            }

            System.out.println("검색된 url은 다음과 같습니다.");
            for(String url : youtubeUrls){
                System.out.println(url);
            }

        } catch (GeneralSecurityException | GoogleJsonResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return youtubeUrls;
    }
}

