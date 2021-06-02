package com.shelter.employeeback.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEmployeeBodyDto extends CreateEmployeeBodyDto{
    private long id;

    public UpdateEmployeeBodyDto(long id, String name, String rolPosition, String gender, boolean hasCourses, String[] courses) {
        super(name, rolPosition, gender, hasCourses, courses);
        this.id = id;
    }
}
