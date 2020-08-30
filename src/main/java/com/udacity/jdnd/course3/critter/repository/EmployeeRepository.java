package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //TODO: refactor query
    @Query(value = "select distinct s.employee_id FROM employee_skill_set s, employee_availability a where s.employee_skill in (:skills) and a.day_of_week= :dayOfWeek and a.employee_id=s.employee_id", nativeQuery = true)
    List<Long> findEmployeesForService(@Param("skills") List<String> skills, @Param("dayOfWeek") String dayOfWeek);

    List<Employee> findAllByIdIsIn(List<Long> empIds);

}
