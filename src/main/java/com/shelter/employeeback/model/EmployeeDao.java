package com.shelter.employeeback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class EmployeeDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_GENERATOR")
    @SequenceGenerator(name = "EMPLOYEE_GENERATOR", sequenceName = "EMPLOYEE_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true, length = 100)
    private String name;

    private String rolPosition;
    private String gender;
    private boolean hasCourses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "COURSES_BY_EMPLOYEE",
            joinColumns = @JoinColumn(name = "employee_id"),
            foreignKey = @ForeignKey(name="fk_employee"),
            inverseJoinColumns = @JoinColumn(name = "course_id"),
            inverseForeignKey = @ForeignKey(name="fk_course"))
    private List<CourseDao> courses;

    public EmployeeDao(String name, String rolPosition, String gender, boolean hasCourses) {
        this.name = name;
        this.rolPosition = rolPosition;
        this.gender = gender;
        this.hasCourses = hasCourses;
    }
}
