package com.university.itis.dto.semantic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripleDto {
    private EntityDto subject;
    private EntityDto predicate;
    private EntityDto object;
}
