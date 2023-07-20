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
        userMessage.put("content", "너는 할머니 상담사야. 너는 매우 친절하고, 내담자에게 부정적인 말을 하기보다는 최대한 긍정적인 방향으로 해결책을 제시해주려고 해.\n"
                + "내담자는 6가지 해결 중심 상담기법에 의거한 질문에 따라 답을 했어. 질문과 내담자의 답을 제공해줄테니, 형식에 따라 상담해줘.\n"
                +"1번 질문: 어떤 고민을 가지고 있는지 설명해주실 수 있을까요?\n"
                +"1번 대답: "+ diary.getAnswer1()+"\n"
                +"2번 질문:  그 일이 있었을 때 어떤 기분이 들었나요?\n"
                +"2번 대답: "+diary.getAnswer2()+"\n"
                +"3번 질문:  상대방은 왜 그렇게 행동했을 것이라 생각하나요?\n"
                +"3번 대답: "+diary.getAnswer3()+"\n"
                +"4번 질문:  만약 상황이 해결된다면, 어떤 변화를 기대하나요?\n"
                +"4번 대답: "+diary.getAnswer4()+"\n"
                +"5번 질문:  비슷한 과거의 경험에서 문제를 해결해 본 적이 있나요?\n"
                +"5번 대답: "+diary.getAnswer5()+"\n"
                +"6번 질문:  문제 해결을 위해 지금 시도할 수 있는 가장 작은 변화는 무엇일까요?\n"
                +"6번 대답: "+diary.getAnswer6()+"\n"
                + "내담자의 대답을 통해 너가 분석해야 하는 내용과 형식은 다음과 같아. :\n"
                + "<상담사인 너의 분석 형식> \n"
                + "0. 고민 카테고리 분류: [인간관계, 건강, 재물, 진로] 중에서 선택"
                + "1. 객관적인 상황 분석, 요약 (2줄)\n"
                + "2. 내담자의 감정 분석, 추측 (2줄)\n"
                + "3. 내담자의 고민거리인 상대방의 입장 분석, 추측\n"
                + "4. 대안 제시: 내담자의 5, 6번 답변을 바탕으로 해결책 구체화\n"
                + "5. 내담자의 4번 답변 바탕으로 해결 완료 시, 기대 효과 제시\n"
                + "6. 상담 내용을 키워드로 추출 - 내담자가 말한 내용"
                + "결과는 아래의 JSON 형식을 무조건 지켜서 답해줘. \n"
                + "{ \"category\" : \"\", \"q1Explanation\": \"\", \"q2Explanation\": \"\", \"q3Explanation\": \"\", \"q4Explanation\": \"\", \"q5Explanation\": \"\", \"keywords\": []\n");

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
                content = content.replace("'", "\"");  // replace ' with " to make a valid JSON string
                System.out.println(content);
                JSONObject resultJson = new JSONObject(content);

                analysis.setCategory(resultJson.getString("category"));

                analysis.setQ1Explanation(resultJson.getString("q1Explanation"));
                analysis.setQ2Explanation(resultJson.getString("q2Explanation"));
                analysis.setQ3Explanation(resultJson.getString("q3Explanation"));
                analysis.setQ4Explanation(resultJson.getString("q4Explanation"));
                analysis.setQ5Explanation(resultJson.getString("q5Explanation"));

                analysis.setKeywords(jsonArrayToList(resultJson.getJSONArray("keywords")));

                return analysis;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            System.out.println("Socket timeout exception");
            throw new IOException("Socket timeout exception", e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception");
            throw new IOException("IO exception", e);
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