package com.university.itis.model;

import org.hibernate.annotations.SortNatural;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.SortedSet;

@Entity
@Table(name = "quiz")
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

    @SortNatural
    @OrderBy
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private SortedSet<Question> questions;

    @SortNatural
    @OrderBy
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private SortedSet<QuizParticipant> participants;

    public Quiz() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SortedSet<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(SortedSet<Question> questions) {
        this.questions = questions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public SortedSet<QuizParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(SortedSet<QuizParticipant> participants) {
        this.participants = participants;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

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

    public boolean isAnyOrder() {
        return isAnyOrder;
    }

    public void setAnyOrder(boolean anyOrder) {
        isAnyOrder = anyOrder;
    }
}
