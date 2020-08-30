package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.Pet;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.mapper.PetMapper;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(PetController.class.getSimpleName());
    private final PetService petService;
    private final PetMapper petMapper;

    public PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        logger.info("request to save a new pet");
        Pet pet = petService.savePet(petDTO.getOwnerId(), petMapper.convertPetDTOToPet(petDTO));
        return petMapper.convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        logger.info("request to get pet by id ");
        Pet pet = petService.findPetById(petId);
        return petMapper.convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        logger.info("request to get all pets ");
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> pets = petService.getPets();
        pets.forEach(pet -> petDTOS.add(petMapper.convertPetToPetDTO(pet)));
        return petDTOS;

    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        logger.info("request to get all pets for owner");
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        pets.forEach(pet -> petDTOS.add(petMapper.convertPetToPetDTO(pet)));
        return petDTOS;
    }
}
