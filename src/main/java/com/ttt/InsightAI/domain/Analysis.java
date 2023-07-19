package com.ttt.InsightAI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "analysis")
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

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public List<String> getQ1Keywords() {
        return q1Keywords;
    }

    public void setQ1Keywords(List<String> q1Keywords) {
        this.q1Keywords = q1Keywords;
    }

    public String getQ1Explanation() {
        return q1Explanation;
    }

    public void setQ1Explanation(String q1Explanation) {
        this.q1Explanation = q1Explanation;
    }

    public List<String> getQ2Keywords() {
        return q2Keywords;
    }

    public void setQ2Keywords(List<String> q2Keywords) {
        this.q2Keywords = q2Keywords;
    }

    public String getQ2Explanation() {
        return q2Explanation;
    }

    public void setQ2Explanation(String q2Explanation) {
        this.q2Explanation = q2Explanation;
    }

    public List<String> getQ3Keywords() {
        return q3Keywords;
    }

    public void setQ3Keywords(List<String> q3Keywords) {
        this.q3Keywords = q3Keywords;
    }

    public String getQ3Explanation() {
        return q3Explanation;
    }

    public void setQ3Explanation(String q3Explanation) {
        this.q3Explanation = q3Explanation;
    }

    public List<String> getQ4Keywords() {
        return q4Keywords;
    }

    public void setQ4Keywords(List<String> q4Keywords) {
        this.q4Keywords = q4Keywords;
    }

    public String getQ4Explanation() {
        return q4Explanation;
    }

    public void setQ4Explanation(String q4Explanation) {
        this.q4Explanation = q4Explanation;
    }

    public List<String> getEmotionKeywords() {
        return emotionKeywords;
    }

    public void setEmotionKeywords(List<String> emotionKeywords) {
        this.emotionKeywords = emotionKeywords;
    }

    public String getEmotionExplanation() {
        return emotionExplanation;
    }

    public void setEmotionExplanation(String emotionExplanation) {
        this.emotionExplanation = emotionExplanation;
    }
}