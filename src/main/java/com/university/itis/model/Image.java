package com.university.itis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image extends AbstractEntity {
    @Column
    private String name;
}
