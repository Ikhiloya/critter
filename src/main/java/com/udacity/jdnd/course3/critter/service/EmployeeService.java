package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.Employee;
import com.udacity.jdnd.course3.critter.domain.EmployeeAvailability;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeAvailabilityRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillSetRepository employeeSkillSetRepository;
    private final EmployeeAvailabilityRepository employeeAvailabilityRepository;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeSkillSetRepository employeeSkillSetRepository, EmployeeAvailabilityRepository employeeAvailabilityRepository) {
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


    public List<Employee> findEmployeesForService(List<String> skills, String dayOfWeek) {
        List<Long> empIds = employeeRepository.findEmployeesForService(skills, dayOfWeek);
        return employeeRepository.findAllByIdIsIn(empIds);
    }
}
