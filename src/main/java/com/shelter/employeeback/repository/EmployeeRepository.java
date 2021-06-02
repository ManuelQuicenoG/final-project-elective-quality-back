package com.shelter.employeeback.repository;

import com.shelter.employeeback.model.EmployeeDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeDao, Long> {
    EmployeeDao findByName(String name);
    @Query(value = "INSERT INTO COURSES_BY_EMPLOYEE (EMPLOYEE_ID, COURSE_ID) VALUES (?1, ?2)", nativeQuery = true)
    void insert(long employee_id, long course_id);

    @Query(value = "delete from courses_by_employee where employee_id=?1", nativeQuery = true)
    void delete(long employee_id);

    @Query(value = "select a.id from course a where a.name = ?1", nativeQuery = true)
    long search(String name);
}
