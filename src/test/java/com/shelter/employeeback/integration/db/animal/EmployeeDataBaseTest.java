package com.shelter.employeeback.integration.db.employee;

import com.shelter.employeeback.model.EmployeeDao;
import com.shelter.employeeback.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:integration-test.yml"})
@Testcontainers
public class EmployeeDataBaseTest {
    @Container
    private static PostgreSQLContainer database = new PostgreSQLContainer("postgres:13.2");

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void createEmployee() {
        var employee = new EmployeeDao("Hela", "Mestizo", "Female", true);
        var employeeDb = employeeRepository.save(employee);

        assertThat(employeeDb, notNullValue());
        assertThat(employeeDb.getName(), equalTo("Hela"));
        assertThat(employeeDb.getRolPosition(), equalTo("Mestizo"));
        assertThat(employeeDb.getGender(), equalTo("Female"));
        assertThat(employeeDb.isHasCourses(), equalTo(true));
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }
}
