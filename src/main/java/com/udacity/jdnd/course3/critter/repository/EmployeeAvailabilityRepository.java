package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.EmployeeAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAvailabilityRepository extends JpaRepository<EmployeeAvailability, Long> {
}
