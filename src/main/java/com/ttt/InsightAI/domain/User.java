package com.ttt.InsightAI.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity @Getter @Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @JsonManagedReference(value="d_user")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Diary> diaries = new HashSet<>();

    @JsonManagedReference(value="a_user")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Analysis> analyses = new HashSet<>();

    //constructer

    public User(){}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(Long id, String name, String password, Set<Diary> diaries, Set<Analysis> analyses) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.diaries = diaries;
        this.analyses = analyses;
    }

}