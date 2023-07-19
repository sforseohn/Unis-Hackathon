package com.ttt.InsightAI.service;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.Diary;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OpenAiService {
    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    private final String apiKey;
    private final OkHttpClient client;


    public OpenAiService() {
        this.apiKey = API_KEY;
        // Create OkHttpClient with a timeout of 300 seconds
        this.client = new OkHttpClient.Builder()
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
    }

    public Analysis fetchDiaryAnalysis(Diary diary, Analysis analysis) throws IOException {
        JSONArray messagesArray = new JSONArray();

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "system");
        userMessage.put("content", diary.getContent()+"\n이것은 사용자의 일기야. 나는 이 일기를 분석해서 사용자의 성향을 분석하려고 해.\n"
                + "사용자가 분석 결과를 통해서 얻고 싶은 것은 다음과 같아. :\n"
                + "1. 자신의 흥미와 관심사를 토대로 어떤 직업을 택했을 때 즐겁게 할 수 있을지 찾고자 함.\n"
                + "2. 장단점을 알고 이를 고치고자 함.\n"
                + "3. 자신의 역량을 찾고 이를 개발하고 싶음.\n"
                + "4. 스트레스의 원인을 파악하고 올바르게 해소하고 싶음.\n"
                + "각 질문에 대한 결과를 사용자의 일기에서 키워드를 뽑아내고, 그에 대한 부연설명을 추가해서 결과별 설명에 넣어줘. 부연설명은 최대한 간단하게 해줘 (1~2문장).\n"
                + "+감정키워드 명령어: 일기 내용 중 감정 키워드를 뽑고, 그에 대한 근거를 알려줘."
                + "중요! 예시로 든 아래의 JSON 형식을 따라야 해. 각각의 키워드는 JSON ARRAY 형식으로 알려줘. \n"
                + "{ \"q1Keywords\" : , \"q1Explanation\": , \"q2Keywords\" : , \"q2Explanation\": ,\"q3Keywords\" : , \"q3Explanation\": ,\"q4Keywords\" : , \"q4Explanation\": ,\"emotionKeywords\": , \"emotionExplanation\": }\n"
                + "\n실제 예시를 하나 들어줄게. {\"q1Keywords\" : [\"책\", \"읽기\"], \"q1Explanation\": \"문학, 출판, 교육 등과 관련된 직업을 즐겁게 할 수 있을 것입니다.\",\n" +
                "\"q2Keywords\" : [\"활짝 열리다\", \"맑다\", \"평온하다\"], \"q2Explanation\": \"긍정적인 마인드를 가지고 있습니다. 하지만 한가지 주의해야 할 점은 동기부여가 필요할 때가 있을 것입니다.\",\n" +
                "\"q3Keywords\" : [\"산책\", \"햇살\", \"커피\"], \"q3Explanation\": \"자연과 사람과의 소통을 통해 역량을 발전시킬 수 있으며, 음료나 식품과 관련된 직업이 적합할 수 있습니다.\",\n" +
                "\"q4Keywords\" : [\"평온한\", \"맑다\"], \"q4Explanation\": \"스트레스 원인 파악이 어려울 것입니다. 꾸준한 휴식이 필요할 수 있습니다.\",\n" +
                "\"emotionKeywords\": [\"활짝\", \"열리다\", \"맑다\", \"평온한\"], \"emotionExplanation\": \"일상적인 활동에 긍정적인 감정을 느끼고 즐길 수 있습니다.\"}");


        messagesArray.put(userMessage);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messagesArray);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());

        Request request = new Request.Builder()
                .url(OPENAI_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject responseJson = new JSONObject(responseBody);
                JSONObject choicesObject = responseJson.getJSONArray("choices").getJSONObject(0);
                String content = choicesObject.getJSONObject("message").getString("content");
                System.out.println(content);
                content = content.replace("'", "\"");  // replace ' with " to make a valid JSON string
                System.out.println(content);
                JSONObject resultJson = new JSONObject(content);

                analysis.setQ1Keywords(jsonArrayToList(resultJson.getJSONArray("q1Keywords")));
                analysis.setQ1Explanation(resultJson.getString("q1Explanation"));

                analysis.setQ2Keywords(jsonArrayToList(resultJson.getJSONArray("q2Keywords")));
                analysis.setQ2Explanation(resultJson.getString("q2Explanation"));

                analysis.setQ3Keywords(jsonArrayToList(resultJson.getJSONArray("q3Keywords")));
                analysis.setQ3Explanation(resultJson.getString("q3Explanation"));

                analysis.setQ4Keywords(jsonArrayToList(resultJson.getJSONArray("q4Keywords")));
                analysis.setQ4Explanation(resultJson.getString("q4Explanation"));

                analysis.setEmotionKeywords(jsonArrayToList(resultJson.getJSONArray("emotionKeywords")));
                analysis.setEmotionExplanation(resultJson.getString("emotionExplanation"));

                return analysis;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            System.out.println("Socket timeout exception");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception");
        }
        return null;
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}