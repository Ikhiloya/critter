package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.EmployeeSkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSkillSetRepository extends JpaRepository<EmployeeSkillSet, Long> {
    List<EmployeeSkillSet> findAllByEmployeeIdIn(List<Long> employeeId);
}
