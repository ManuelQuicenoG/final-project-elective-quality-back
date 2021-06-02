package com.shelter.employeeback.component.api.employee;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.employeeback.repository.EmployeeRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class SaveEmployeeTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @SneakyThrows
    public void createEmployeeSuccessful() {
        var employee = new CreateEmployeeRequestBody();
        employee.setName("Empleadanew");
        employee.setRolPosition("Seguridad");
        employee.setGender("Female");
        employee.setHasCourses(true);
        employee.setCourses(new ArrayList<String>());

        var createEmployeeRequestBody = new ObjectMapper().writeValueAsString(employee);
        var response = mockMvc.perform(
                post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createEmployeeRequestBody))
                .andReturn()
                .getResponse();

        System.err.println(createEmployeeRequestBody+": ===== "+response.getContentAsString());

        var employeeResponse =
                new ObjectMapper().readValue(response.getContentAsString(), CreateEmployeeResponse.class);

        // Asserts Http Response
        assertThat(employeeResponse.getName(), equalTo("Empleadanew"));
        assertThat(employeeResponse.getRolPosition(), equalTo("Seguridad"));
        assertThat(employeeResponse.getGender(), equalTo("Female"));
        assertThat(employeeResponse.isHasCourses(), equalTo(true));
        assertThat(employeeResponse.getId(), notNullValue());

        // Database Asserts
        var dbQuery = employeeRepository.findById(employeeResponse.getId());
        assertThat(dbQuery.isPresent(), is(true));

        var employeeDB = dbQuery.get();
        assertThat(employeeDB.getName(), equalTo("Empleadanew"));
        assertThat(employeeDB.getRolPosition(), equalTo("Seguridad"));
        assertThat(employeeDB.getGender(), equalTo("Female"));
        assertThat(employeeDB.isHasCourses(), equalTo(true));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public class CreateEmployeeRequestBody {
        private String name;
        private String rolPosition;
        private String gender;
        private boolean hasCourses;
        private List<String> courses;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class CreateEmployeeResponse {
        private Long id;
        private String name;
        private String rolPosition;
        private String gender;
        private boolean hasCourses;
        private List<String> courses;
    }
}
