package com.udacity.jdnd.course3.critter.domain;

import com.udacity.jdnd.course3.critter.user.domain.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<EmployeeSchedule> employeeSchedules;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PetSchedule> petSchedules;

    private LocalDate date;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EmployeeSchedule> getEmployeeSchedules() {
        return employeeSchedules;
    }

    public void setEmployeeSchedules(List<EmployeeSchedule> employeeSchedules) {
        this.employeeSchedules = employeeSchedules;
    }

    public List<PetSchedule> getPetSchedules() {
        return petSchedules;
    }

    public void setPetSchedules(List<PetSchedule> petSchedules) {
        this.petSchedules = petSchedules;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }


}
