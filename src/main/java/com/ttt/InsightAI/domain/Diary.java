package com.ttt.InsightAI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference(value="d_user")
    @JoinColumn(name = "user_id")
    private User user;

    //질문에 대한 답
    private String title;
    private String content;
    private LocalDateTime date;

    @OneToOne(mappedBy = "diary", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference(value="a_diary")
    private Analysis analysis;

}