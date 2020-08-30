package com.udacity.jdnd.course3.critter.domain;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nationalized
    private String name;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<EmployeeSkillSet> employeeSkillSet;


    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<EmployeeAvailability> employeeAvailabilities;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkillSet> getEmployeeSkillSet() {
        return employeeSkillSet;
    }

    public void setEmployeeSkillSet(Set<EmployeeSkillSet> employeeSkillSet) {
        this.employeeSkillSet = employeeSkillSet;
    }

    public Set<EmployeeAvailability> getEmployeeAvailabilities() {
        return employeeAvailabilities;
    }

    public void setEmployeeAvailabilities(Set<EmployeeAvailability> employeeAvailabilities) {
        this.employeeAvailabilities = employeeAvailabilities;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employeeSkillSet=" + employeeSkillSet +
//                ", employeeAvailabilities=" + employeeAvailabilities +
                '}';
    }
}
