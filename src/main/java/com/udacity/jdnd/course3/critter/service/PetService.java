package com.udacity.jdnd.course3.critter.service;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.user.domain.Customer;
import com.udacity.jdnd.course3.critter.domain.Pet;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    private final Logger logger = LoggerFactory.getLogger(PetService.class.getSimpleName());

    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public PetService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Pet savePet(Long ownerId, Pet pet) {
        Optional<Customer> optionalCustomer = customerRepository.findById(ownerId);
        if (!optionalCustomer.isPresent()) {
            throw new CustomerNotFoundException();
        }
        Customer customer = optionalCustomer.get();
        pet.setCustomer(customer);
        Pet newPet = petRepository.save(pet);
        customer.setPets(Lists.newArrayList(newPet));
        return newPet;
    }

    public Pet findPetById(long petId) {
        return petRepository
                .findById(petId)
                .orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }

    public Customer getOwnerByPet(long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(PetNotFoundException::new);
        return customerRepository.findById(pet.getOwnerId())
                .orElseThrow(CustomerNotFoundException::new);
    }
}
