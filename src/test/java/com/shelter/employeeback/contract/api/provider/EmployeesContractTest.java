package com.shelter.employeeback.contract.api.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import com.shelter.employeeback.controller.EmployeeController;
import com.shelter.employeeback.domain.Employee;
import com.shelter.employeeback.service.interfaces.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PactBroker(
        url = "${PACT_BROKER_BASE_URL}",
        authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}")
)
@Provider("EmployeeShelterBack")
@ExtendWith(MockitoExtension.class)
public class EmployeesContractTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    public void changeContext(PactVerificationContext context) {
        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(employeeController);
        context.setTarget(testTarget);
    }

    @State("has employees")
    public void listEmployees() {
        Employee employee = new Employee();
        employee.setName("empleada");
        employee.setGender("Female");
        employee.setRolPosition("Seguridad");
        employee.setHasCourses(true);

        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);

        Mockito.when(employeeService.getAll()).thenReturn(employees);
    }

    @State("backend service delete is ready")
    public void noOneEmployee() {
        String name = "anyone_that_not_exist";
        Mockito.doAnswer((i) -> {
            assertEquals(name, i.getArgument(0));
            return null;
        }).when(employeeService).delete(name);
    }

    @State("backend service save is ready")
    public void newEmployee() {
        Employee employee = new Employee();
        employee.setName("new");
        employee.setGender("Female");
        employee.setRolPosition("any");
        employee.setHasCourses(true);

        Mockito.when(employeeService.save(Mockito.any(Employee.class))).thenReturn(employee);
    }

}