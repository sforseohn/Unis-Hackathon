package com.ttt.InsightAI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "analysis")
@Getter @Setter
public class Analysis { //gpt의 대답 - 분석 결과
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JsonBackReference(value = "a_diary")
    private Diary diary;

    @ManyToOne
    @JsonBackReference(value = "a_user")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    // Question 1
//    @ElementCollection
//    private List<String> q1Keywords;
//    @Column
//    private String  q1Explanation;

    //gpt 분석결과지
    //객관적인 상황 분석, 요약 (2줄)
    @Column
    private String  q1Explanation;

    //내담자의 감정 분석, 추측 (2줄)
    @Column
    private String q2Explanation;

    //내담자의 고민거리인 상대방의 입장 분석, 추측
    @Column
    private String q3Explanation;

    //대안 제시: 내담자의 5, 6번 답변을 바탕으로 해결책 구체화
    @Column
    private String q4Explanation;

    // 내담자의 4번 답변 바탕으로 해결 완료 시, 기대 효과 제시
    @Column
    private String q5Explanation;

    //내담자가 말한 내용을 키워드로 추출
    @ElementCollection
    private List<String> keywords;

}