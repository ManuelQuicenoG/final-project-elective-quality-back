package com.shelter.employeeback.component.api.employee;

import com.shelter.employeeback.model.EmployeeDao;
import com.shelter.employeeback.repository.EmployeeRepository;
import lombok.SneakyThrows;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
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
public class ListEmployeesTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        var employee = new EmployeeDao("New employee", "Administrativa", "Male", false);
        employeeRepository.save(employee);
    }

    @Test
    @SneakyThrows
    public void listEmployeesSuccessfully() {
        var response = mockMvc.perform(get("/employees")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    @SneakyThrows
    public void listEmployeesWithRightSchema() {
        var response = mockMvc.perform(get("/employees")).andReturn().getResponse();

        var jsonSchema = new JSONObject(new JSONTokener(ListEmployeesTest.class.getResourceAsStream("/employees.json")));
        var jsonArray = new JSONArray(response.getContentAsString());

        var schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonArray);
    }
}
