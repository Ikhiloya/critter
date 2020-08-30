package com.udacity.jdnd.course3.critter.dto.mapper;

import com.udacity.jdnd.course3.critter.domain.Pet;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    private Logger logger = LoggerFactory.getLogger(PetMapper.class.getSimpleName());

    public PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    public Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
