package com.shelter.employeeback.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
    private long id;
    private String name;
    private String rolPosition;
    private String gender;
    private boolean hasCourses;
    private String[] courses;

    public EmployeeDto(long id, String name, String rolPosition, String gender, boolean hasCourses, String[] courses) {
        this.id = id;
        this.name = name;
        this.rolPosition = rolPosition;
        this.gender = gender;
        this.hasCourses = hasCourses;
        this.courses = courses;
    }
}
