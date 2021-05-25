package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image extends AbstractEntity {
    @Column
    private String name;
}
