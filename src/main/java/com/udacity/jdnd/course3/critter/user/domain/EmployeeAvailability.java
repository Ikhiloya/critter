package com.udacity.jdnd.course3.critter.user.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;

@Entity
public class EmployeeAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    @Column(insertable = false, updatable = false)
    private Long employeeId;

    public EmployeeAvailability() {
    }

    public EmployeeAvailability(@NotNull DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
        return "EmployeeAvailability{" +
                "id=" + id +
                ", dayOfWeek=" + dayOfWeek +
                ", employee=" + employee +
                ", employeeId=" + employeeId +
                '}';
    }
}
