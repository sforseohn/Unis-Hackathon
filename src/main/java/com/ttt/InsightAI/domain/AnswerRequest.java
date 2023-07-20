package com.ttt.InsightAI.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter @Setter
public class AnswerRequest {
    private Long id;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    private String answer5;

    @Column
    private String answer6;
}
