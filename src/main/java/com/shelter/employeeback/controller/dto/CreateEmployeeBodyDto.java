package com.shelter.employeeback.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateEmployeeBodyDto {
    private String name;
    private String rolPosition;
    private String gender;
    private boolean hasCourses;
    private String[] courses;

    public CreateEmployeeBodyDto(String name, String rolPosition, String gender, boolean hasCourses, String[] courses) {
        this.name = name;
        this.rolPosition = rolPosition;
        this.gender = gender;
        this.hasCourses = hasCourses;
        this.courses = courses;
    }
}
