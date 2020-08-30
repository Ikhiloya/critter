package com.udacity.jdnd.course3.critter.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class PetSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleId", nullable = false)
    private Schedule schedule;
    @NotNull
    private Long petId;

    public PetSchedule() {
    }

    public PetSchedule(Long petId) {
        this.petId = petId;
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

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

}
