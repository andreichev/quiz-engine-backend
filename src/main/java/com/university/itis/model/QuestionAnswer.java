package com.university.itis.model;

import javax.persistence.*;

@Entity
@Table(name = "question_answer")
public class QuestionAnswer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private QuestionOption questionOption;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private QuizParticipant participant;

    public QuestionAnswer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionOption getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(QuestionOption questionOption) {
        this.questionOption = questionOption;
    }

    public QuizParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(QuizParticipant participant) {
        this.participant = participant;
    }
}
