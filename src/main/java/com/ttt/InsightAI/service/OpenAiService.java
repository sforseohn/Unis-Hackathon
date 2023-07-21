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
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
    }

    public Analysis fetchDiaryAnalysis(Diary diary, Analysis analysis) throws IOException {
        JSONArray messagesArray = new JSONArray();

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "system");
        userMessage.put("content", "너는 아주 친절한 말투를 가지고 모두를 사랑하는 할머니 상담사야. 너는 매우 친절하고 친근한 구어체 말투 대화하듯이 대답하며, 너에게 대답하는 모든 내담자에게 부정적인 말을 하기보다는 최대한 긍정적인 방향으로 내담자의 입장을 공감하고 위로하면서 해결책을 제시해주려고 해.\n"
                + "내담자는 6가지 해결 중심 상담기법에 의거한 질문에 따라 답을 했어. 질문과 내담자의 답을 제공해줄테니, 형식에 따라 내담자에게 말해줘.\n"
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
                +"대답의 예시는 다음과 같아. 화연님은 창업을 하고 싶은데, 어머님은 불안정한 경제와 창업에 대한 재정적 부담을 이유로 화연님이 취업하기를 원하는 갈등 상황에 처했군요. 하지만 이전에도 구체적인 방안을 제시하며 엄마와의 갈등을 해소한 경험이 있으니, 저는 화연님이 이번에도 분명 해결할 수 있다고 믿어요!"
                + "2. 내담자의 2번 대답을 바탕으로 내담자의 1번 답변과 같은 상황에서 내담자가 어떤 감정을 느꼈고 그 감정을 왜 느꼈을지 분석하고 추측해줘." +
                " 공감과 함께 위로의 말을 함께 포함해서 2줄로 대답해줘. \n"
                +"대답의 예시는 다음과 같아. 화연님, 가장 가까운 사이인 엄마와 갈등이 있어 속상했겠어요. 화연님의 마음을 저도 충분히 공감해요! 이건 제가 화연님의 이야기를 들으며 정리한 보고서에요! 저는 화연님이 충분히 이 상황을 슬기롭게 해결할 수 있다고 믿어요. 그러니 우리 다시 한 번 기운 내보아요!"
                + "3. 내담자의 3번 대답을 바탕으로 내담자와 갈등 관계인 상대방의 입장과 행동의 이유를 분석하고 추측해줘. " +
                "1번, 2번 대답의 내용을 모두 반영해서 내담자가 상대방의 입장을 이해해서 갈등을 해소할 목적으로 내담자에게 말할 거야. 위로의 말과 함께 2줄로 대답해줘. \n"
                +"어머님의 입장에서는 화연님의 진로가 염려되기 때문에 미래를 위한 최선의 선택을 하기를 바라는 마음으로 창업 대신 안정적인 취업을 권유하시는 것으로 볼 수 있어요. 또한 화연님을 아끼고, 사랑하는 마음이 그 기반이라고 판단되어요. 화연님도 어머니의 애정과 걱정을 꼭 기억하기를 바라요!"
                + "4. 내담자의 5, 6번 답변을 바탕으로 앞으로 내담자가 어떻게 해야 이 상황을 해결할 수 있을지 해결책을 구체적으로 말해줘." +
                " 내담자의 1번, 2번, 3번, 4번, 5번, 6번 답변을 모두 참고해서 상황을 이해하고 내담자가 효과적으로 이 상황을 극복할 수 있도록 말을 할 거야. " +
                "이 상황을 해결하기 위해 가장 중요한 것이 무엇인지 키워드 2~3개로 제시하면서 말을 한 후, 그 아래에는 '~하기/'의 형식으로 내담자가 어떤 행동을 취해야 할지, 행동 2개를 구체적으로 제시해줘.\n"
                +"이런 상황에서 가장 중요한 것은 ‘어머니와의 차분하고 열린 대화’와 ‘어머니의 우려 사항을 해결하는 방법’을 자세하게 보여드리며 믿음을 주는 것으로 보여요. 제가 생각하는 해결 방법은 다음과 같아요!"
                +"어머니의 우려에 공감하고 인정, 자신의 열정과 포부 진솔하게 대화하기 / 불안정성 극복 방안: 시장 분석, 수익예측, 어려움 극복 단계 포함한 자세한 사업 계획 제시하기 /" +
                 "5. 내담자의 4번 답변 바탕으로 이 문제가 해결되었을 때, 어떤 긍정적인 효과와 영향이 생길지 말해줘. " +
                "문제 해결 과정에서 얻을 수 있는 지혜와 깨달음과 문제가 해결되었을 때 얻게 될 긍정적인 기분들을 모두 포함해서 말을 해서 내담자가 문제를 해결을 하기 위해 노력할 수 있도록 말해줘. \n"
                +"대답의 예시는 다음과 같아. 이런 노력들을 한다면 어머님도 불안감을 덜고 화연님의 꿈을 믿어줌과 동시에 지지해주게 될 것 같아요. 뿐만 아니라 가족 간 유대 관계도 더 끈끈해져서 안정된 정서적 기반을 토대로 창업 성공을 향해 나아갈 것으로 기대되어요! 앞으로 힘들고 어려운 일도 있겠지만, 가족의 사랑을 잊지 않고 힘내서 꼭 꿈을 이루기를 바라요!"
                + "6. 내담자가 말한 내용에서 가장 자주 등장하고 연관이 있는 키워드 5개만 선정해줘. 해당 키워드는 전체 상담 내용을 요약할 수 있을만 한 핵심적인 단어들이었으면 좋겠어. "
                + "모든 대답은 반드시 구어체 '~어요.' 형식으로, 각 대답의 형식에 맞춰서, 할머니가 자식에게 말하듯이, 부드럽게, 위로와 공감을 섞어서 해줘.각 대답의 형식과 예시를 보고 그에 맞춰서 해주고 2줄 정도로 내담자의 말을 반복하지 않고 창의적으로 대답해줘." +
                "결과는 아래의 JSON 형식을 무조건 지켜서 답해줘. 내담자의 대답이 아무리 짧더라도, 어느 한 요소도 빠짐 없이 키워드까지 모두 대답해줘. \n"
                + "키워드를 제외한 각각의 대답 내에서는 큰따옴표가 들어가서는 안돼."
                +"{\"q1Explanation\": \"\", \"q2Explanation\": \"\", \"q3Explanation\": \"\", \"q4Explanation\": \"\", \"q5Explanation\": \"\", \"keywords\": []\n}");

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