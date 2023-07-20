package com.ttt.InsightAI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "present")
@Getter @Setter
public class DiaryAnalysisRequest { //선물(유튜브, 책)
//    private Long userId;
//    private String title;
//    private String content;
//    private LocalDateTime date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JsonBackReference(value = "p_diary")
    private Diary diary;

    @ManyToOne
    @JsonBackReference(value = "p_user")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    private List<String> youtubeUrl;

    @ElementCollection
    private List<String> bookUrl;



}
