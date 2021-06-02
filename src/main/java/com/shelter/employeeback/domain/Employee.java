package com.shelter.employeeback.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Employee {
    private String name;
    private String rolPosition;
    private String gender;
    private boolean hasCourses;
    private String[] courses;
    private long id;

    public Employee(long id, String name, String rolPosition, String gender, boolean hasCourses, String[] courses) {
        this(name, rolPosition, gender, hasCourses, courses);
        this.id = id;
    }

    public Employee(String name, String rolPosition, String gender, boolean hasCourses, String[] courses) {
        this.name = name;
        this.rolPosition = rolPosition;
        this.gender = gender;
        this.hasCourses = hasCourses;
        this.courses = courses;
    }
}
