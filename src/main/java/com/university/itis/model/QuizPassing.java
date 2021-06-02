package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_passing")
@Getter
@Setter
public class QuizPassing extends AbstractEntity implements Comparable<QuizPassing> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "passing", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<QuestionAnswer> answers = new ArrayList<>();

    @Override
    public int compareTo(QuizPassing o) {
        return (int) (this.getId() - o.getId());
    }
}
