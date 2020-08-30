package com.udacity.jdnd.course3.critter.domain;

import com.udacity.jdnd.course3.critter.user.domain.Customer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PetType type;
//    @Nationalized
    private String name;
    private LocalDate birthDate;
    private String notes;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId", nullable = false)
    private Customer customer;

    @Column(insertable = false, updatable = false)
    private Long ownerId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", notes='" + notes + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
