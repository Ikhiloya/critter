package com.udacity.jdnd.course3.critter.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class EmployeeSkillSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeSkill employeeSkill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    @Column(insertable = false, updatable = false)
    private Long employeeId;

    public EmployeeSkillSet() {
    }

    public EmployeeSkillSet(@NotNull EmployeeSkill employeeSkill) {
        this.employeeSkill = employeeSkill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeSkill getEmployeeSkill() {
        return employeeSkill;
    }

    public void setEmployeeSkill(EmployeeSkill employeeSkill) {
        this.employeeSkill = employeeSkill;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "EmployeeSkillSet{" +
                "id=" + id +
                ", employeeSkill=" + employeeSkill +
//                ", employee=" + employee +
//                ", employeeId=" + employeeId +
                '}';
    }
}
