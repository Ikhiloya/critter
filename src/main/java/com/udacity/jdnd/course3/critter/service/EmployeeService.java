package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.*;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeAvailabilityRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeScheduleRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillSetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @PersistenceContext
    EntityManager entityManager;
    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillSetRepository employeeSkillSetRepository;
    private final EmployeeAvailabilityRepository employeeAvailabilityRepository;


    private static final String GET_EMPLOYEES_FOR_SERVICE =
            "select distinct s.employeeId FROM EmployeeSkillSet s, EmployeeAvailability a where s.employeeSkill in (:skills) and a.dayOfWeek= :dayOfWeek and a.employeeId=s.employeeId";


    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeSkillSetRepository employeeSkillSetRepository,
                           EmployeeAvailabilityRepository employeeAvailabilityRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeSkillSetRepository = employeeSkillSetRepository;
        this.employeeAvailabilityRepository = employeeAvailabilityRepository;
    }

    public Employee save(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        if (savedEmployee.getEmployeeSkillSet() != null && !savedEmployee.getEmployeeSkillSet().isEmpty()) {
            savedEmployee.getEmployeeSkillSet().forEach(employeeSkillSet -> {
                employeeSkillSet.setEmployee(savedEmployee);
                employeeSkillSetRepository.save(employeeSkillSet);
            });
        }
        if (savedEmployee.getEmployeeAvailabilities() != null &&
                !savedEmployee.getEmployeeAvailabilities().isEmpty()) {
            savedEmployee.getEmployeeAvailabilities().forEach(empAv -> {
                empAv.setEmployee(savedEmployee);
                employeeAvailabilityRepository.save(empAv);
            });
        }
        return savedEmployee;
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository
                .findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public void setAvailability(Set<EmployeeAvailability> empAvailability, long employeeId) {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        empAvailability.forEach(empAv -> {
            empAv.setEmployee(employee);
            employeeAvailabilityRepository.save(empAv);
        });
        employee.setEmployeeAvailabilities(empAvailability);
    }


//    public List<Employee> findEmployeesForService(Set<EmployeeSkill> employeeSkills, List<String> skills, String dayOfWeek) {
//        List<Long> empIds = employeeRepository.findEmployeesForService(skills, dayOfWeek);
//        List<Employee> employees = employeeRepository.findAllByIdIsIn(empIds);
//        return employees.stream()
//                .filter(emp ->
//                        findIntersection(mapEmpSkillSetToEmpSkill(emp.getEmployeeSkillSet()), skills)
//                                .equals(skills))
//                .collect(Collectors.toList());
//    }
//
//


    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        TypedQuery<Long> query = entityManager.createQuery(GET_EMPLOYEES_FOR_SERVICE, Long.class);
        query.setParameter("skills", skills);
        query.setParameter("dayOfWeek", dayOfWeek);
        List<Long> empIds = query.getResultList();

        List<Employee> employees = employeeRepository.findAllByIdIsIn(empIds);

        return employees.stream()
                .filter(emp ->
                        findIntersection(mapEmpSkillSetToEmpSkill(emp.getEmployeeSkillSet()), skills)
                                .equals(skills))
                .collect(Collectors.toList());

    }

    private Set<EmployeeSkill> findIntersection(Set<EmployeeSkill> empSkills1, Set<EmployeeSkill> empSkills2) {
        Set<EmployeeSkill> intersection = new HashSet<>(empSkills1);
        intersection.retainAll(empSkills2);
        return intersection;
    }

    private Set<EmployeeSkill> mapEmpSkillSetToEmpSkill(Set<EmployeeSkillSet> employeeSkillSets) {
        Set<EmployeeSkill> employeeSkills = new HashSet<>();
        employeeSkillSets.forEach(emp -> {
            employeeSkills.add(emp.getEmployeeSkill());
        });
        return employeeSkills;
    }
}
