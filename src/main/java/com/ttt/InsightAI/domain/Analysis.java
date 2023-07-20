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
public class Analysis {
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

    // Question 1
    @ElementCollection
    private List<String> q1Keywords;
    @Column
    private String q1Explanation;

    // Question 2
    @ElementCollection
    private List<String> q2Keywords;
    @Column
    private String q2Explanation;

    // Question 3
    @ElementCollection
    private List<String> q3Keywords;
    @Column
    private String q3Explanation;

    // Question 4
    @ElementCollection
    private List<String> q4Keywords;
    @Column
    private String q4Explanation;

    // Emotion
    @ElementCollection
    private List<String> emotionKeywords;
    @Column
    private String emotionExplanation;

}