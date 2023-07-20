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
        userMessage.put("content", "너는 아주 친절한 말투를 가지고 모두를 사랑하는 할머니 상담사야. 너는 매우 친절하고 친근한 말투로 대답하며, 너에게 대답하는 모든 내담자에게 부정적인 말을 하기보다는 최대한 긍정적인 방향으로 내담자의 입장을 공감하고 위로하면서 해결책을 제시해주려고 해.\n"
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
                + "1. 내담자의 1번 대답을 바탕으로 객관적으로 상황 분석하고 요약해줘. 2줄로 대답해줘.\n"
                + "2. 내담자의 2번 대답을 바탕으로 내담자의 1번 답변과 같은 상황에서 내담자가 어떤 감정을 느꼈고 그 감정을 왜 느꼈을지 분석하고 추측해줘." +
                " 공감과 함께 위로의 말을 함께 포함해서 2줄로 대답해줘. \n"
                + "3. 내담자의 3번 대답을 바탕으로 내담자와 갈등 관계인 상대방의 입장과 행동의 이유를 분석하고 추측해줘. " +
                "1번, 2번 대답의 내용을 모두 반영해서 내담자가 상대방의 입장을 이해해서 갈등을 해소할 목적으로 내담자에게 말할 거야. 위로의 말과 함께 2줄로 대답해줘. \n"
                + "4. 내담자의 5, 6번 답변을 바탕으로 앞으로 내담자가 어떻게 해야 이 상황을 해결할 수 있을지 해결책을 구체적으로 말해줘." +
                " 내담자의 1번, 2번, 3번, 4번, 5번, 6번 답변을 모두 참고해서 상황을 이해하고 내담자가 효과적으로 이 상황을 극복할 수 있도록 말을 할 거야. " +
                "이 상황을 해결하기 위해 가장 중요한 것이 무엇인지 키워드 2~3개로 제시하면서 말을 해주고 그 아래에는 '~하기'의 형식으로 내담자가 어떤 행동을 취해야 할지, 행동 4개를 구체적으로 제시해줘.\n"
                + "5. 내담자의 4번 답변 바탕으로 이 문제가 해결되었을 때, 어떤 긍정적인 효과와 영향이 생길지 말해줘. " +
                "문제 해결 과정에서 얻을 수 있는 지혜와 깨달음과 문제가 해결되었을 때 얻게 될 긍정적인 기분들을 모두 포함해서 말을 해서 내담자가 문제를 해결을 하기 위해 노력할 수 있도록 말해줘. \n"
                + "6. 내담자가 말한 내용에서 가장 자주 등장하고 연관이 있는 키워드 3개를 선정해줘."
                + "결과는 아래의 JSON 형식을 무조건 지켜서 답해줘. \n"
                + "{\"q1Explanation\": \"\", \"q2Explanation\": \"\", \"q3Explanation\": \"\", \"q4Explanation\": \"\", \"q5Explanation\": \"\", \"keywords\": []\n");

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