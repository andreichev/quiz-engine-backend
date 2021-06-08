package com.university.itis.dto.semantic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripleDto {
    private int counter;

    private String subjectUri;
    private String subjectLabel;

    private String predicateUri;
    private String predicateLabel;

    private String objectUri;
    private String objectLabel;
}
