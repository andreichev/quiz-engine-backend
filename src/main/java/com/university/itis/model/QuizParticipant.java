package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quiz_participant")
@Getter
@Setter
public class QuizParticipant extends AbstractEntity implements Comparable<QuizParticipant> {

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<QuestionAnswer> questionAnswers;

    @Override
    public int compareTo(QuizParticipant o) {
        return (int) (this.getId() - o.getId());
    }
}
