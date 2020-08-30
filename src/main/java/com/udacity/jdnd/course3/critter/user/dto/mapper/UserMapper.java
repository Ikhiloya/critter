package com.udacity.jdnd.course3.critter.user.dto.mapper;

import com.udacity.jdnd.course3.critter.domain.Pet;
import com.udacity.jdnd.course3.critter.user.domain.*;
import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private Logger logger = LoggerFactory.getLogger(UserMapper.class.getSimpleName());

    public List<String> mapEmployeeSkillsToString(Set<EmployeeSkill> skills) {
        List<String> skillList = new ArrayList<>();
        skills.forEach(skill -> skillList.add(skill.name()));
        return skillList;
    }

    public CustomerDTO mapCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPets() != null && !customer.getPets().isEmpty())
            customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return customerDTO;
    }

    public Customer mapCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        employee.getEmployeeSkillSet().forEach(emp -> System.out.println("Skill===>" + emp.getEmployeeSkill().name()));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        Set<EmployeeSkill> employeeSkills = new HashSet<>();
        Set<DayOfWeek> daysOfWeek = new HashSet<>();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        if (employee.getEmployeeSkillSet() != null && !employee.getEmployeeSkillSet().isEmpty()) {
            employee.getEmployeeSkillSet().forEach(empSkill -> employeeSkills.add(empSkill.getEmployeeSkill()));
            employeeDTO.setSkills(employeeSkills);
        }

        if (employee.getEmployeeAvailabilities() != null && !employee.getEmployeeAvailabilities().isEmpty()) {
            employee.getEmployeeAvailabilities().forEach(empAv -> daysOfWeek.add(empAv.getDayOfWeek()));
            employeeDTO.setDaysAvailable(daysOfWeek);
        }
        return employeeDTO;
    }

    public Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        Set<EmployeeSkillSet> employeeSkillSet = new HashSet<>();
        Set<EmployeeAvailability> employeeAvailabilitySet = new HashSet<>();
        employee.setName(employeeDTO.getName());
        if (employeeDTO.getSkills() != null && !employeeDTO.getSkills().isEmpty()) {
            employeeDTO.getSkills().forEach(empSkill -> employeeSkillSet.add(new EmployeeSkillSet(empSkill)));
            employee.setEmployeeSkillSet(employeeSkillSet);
        }
        if (employeeDTO.getDaysAvailable() != null && !employeeDTO.getDaysAvailable().isEmpty()) {
            employeeDTO.getDaysAvailable().forEach(dayOfWeek -> employeeAvailabilitySet.add(new EmployeeAvailability(dayOfWeek)));
            employee.setEmployeeAvailabilities(employeeAvailabilitySet);
        }
        return employee;
    }

    public Set<EmployeeAvailability> mapDayOfWeekToEmployeeAvailability(Set<DayOfWeek> daysAvailable) {
        Set<EmployeeAvailability> empAvSet = new HashSet<>();
        daysAvailable.forEach(dayOfWeek -> empAvSet.add(new EmployeeAvailability(dayOfWeek)));
        return empAvSet;
    }
}
