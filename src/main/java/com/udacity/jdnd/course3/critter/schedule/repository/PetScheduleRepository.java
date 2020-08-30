package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.domain.PetSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetScheduleRepository extends JpaRepository<PetSchedule, Long> {
    List<PetSchedule> findAllByPetId(Long petId);

    List<PetSchedule> findAllByPetIdIn(List<Long> petIds);
}
