package com.shelter.employeeback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COURSE")
public class CourseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSE_GENERATOR")
    @SequenceGenerator(name="COURSE_GENERATOR", sequenceName = "COURSE_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String name;

    public CourseDao(String name) {
        this.name = name;
    }
}
