package com.university.itis.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quiz_passing")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizPassing extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @OneToMany(mappedBy = "passing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAnswer> answers = new ArrayList<>();
}
