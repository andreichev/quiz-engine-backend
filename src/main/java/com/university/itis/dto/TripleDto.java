package com.university.itis.dto;

public class TripleDto {

    private int counter;

    private String subjectUri;
    private String subjectLabel;

    private String predicateUri;
    private String predicateLabel;

    private String objectUri;
    private String objectLabel;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int id) {
        this.counter = id;
    }

    public String getSubjectUri() {
        return subjectUri;
    }

    public void setSubjectUri(String subjectUri) {
        this.subjectUri = subjectUri;
    }

    public String getSubjectLabel() {
        return subjectLabel;
    }

    public void setSubjectLabel(String subjectLabel) {
        this.subjectLabel = subjectLabel;
    }

    public String getPredicateUri() {
        return predicateUri;
    }

    public void setPredicateUri(String predicateUri) {
        this.predicateUri = predicateUri;
    }

    public String getPredicateLabel() {
        return predicateLabel;
    }

    public void setPredicateLabel(String predicateLabel) {
        this.predicateLabel = predicateLabel;
    }

    public String getObjectUri() {
        return objectUri;
    }

    public void setObjectUri(String objectUri) {
        this.objectUri = objectUri;
    }

    public String getObjectLabel() {
        return objectLabel;
    }

    public void setObjectLabel(String objectLabel) {
        this.objectLabel = objectLabel;
    }
}
