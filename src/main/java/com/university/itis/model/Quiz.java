package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quiz")
@Getter
@Setter
public class Quiz extends AbstractEntity implements Comparable<Quiz> {

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column
    private String description;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date startDate;

    @Column(name = "is_any_order")
    private boolean isAnyOrder;

    @Column(name = "active")
    private boolean isActive;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuizPassing> participants;

    @Column(name = "secret")
    private String secret;

    @Override
    public int compareTo(Quiz o) {
        final int BEFORE = -1;
        final int AFTER = 1;
        if (o == null) {
            return BEFORE;
        }
        Date thisDate = this.getStartDate();
        Date oDate = o.getStartDate();
        if (thisDate == null) {
            return AFTER;
        } else if (oDate == null) {
            return BEFORE;
        } else {
            return thisDate.compareTo(oDate);
        }
    }
}
