package com.shelter.employeeback.component.api.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.employeeback.domain.Employee;
import com.shelter.employeeback.model.EmployeeDao;
import com.shelter.employeeback.repository.EmployeeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GetEmployeeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        var employee = new EmployeeDao("Nuevo empleado", "Administrativo", "Male", false);
        employeeRepository.save(employee);
    }

    @Test
    @SneakyThrows
    public void animaDetailWithSuccessStatusCodeAndContentType() {
        var response = mockMvc.perform(get("/employees/Nuevo empleado")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    @SneakyThrows
    public void detailWithTheRightEmployee() {
        var response = mockMvc.perform(get("/employees/Nuevo empleado")).andReturn().getResponse();
        var employee = new ObjectMapper().readValue(response.getContentAsString(), Employee.class);

        assertThat(employee.getName(), equalTo("Nuevo empleado"));
        assertThat(employee.getRolPosition(), equalTo("Administrativo"));
        assertThat(employee.getGender(), equalTo("Male"));
        assertThat(employee.isHasCourses(), equalTo(false));
    }
}
