package com.ttt.InsightAI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter
public class Diary {   //질문에 대한 답 저장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference(value="d_user")
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime date;

    @Column
    private String answer1;

    @Column
    private String answer2;

    @Column
    private String answer3;

    @Column
    private String answer4;

    @Column
    private String answer5;

    @Column
    private String answer6;


    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference(value="a_diary")
    private Analysis analysis;

}