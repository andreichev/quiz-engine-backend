package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
@Getter
@Setter
public class Question extends AbstractEntity implements Comparable<Question> {

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<QuestionAnswer> questionAnswers;

    @SortNatural
    @OrderBy
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<QuestionOption> questionOptions;

    @Override
    public int compareTo(Question o) {
        return (int) (this.getId() - o.getId());
    }
}
