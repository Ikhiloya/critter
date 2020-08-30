package com.udacity.jdnd.course3.critter.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class EmployeeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleId", nullable = false)
    private Schedule schedule;
    @NotNull
    private Long employeeId;

    public EmployeeSchedule() {
    }

    public EmployeeSchedule(Long empId) {
        this.employeeId = empId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}

