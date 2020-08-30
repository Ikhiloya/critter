package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.domain.EmployeeSchedule;
import com.udacity.jdnd.course3.critter.domain.Pet;
import com.udacity.jdnd.course3.critter.domain.PetSchedule;
import com.udacity.jdnd.course3.critter.domain.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeScheduleRepository;
import com.udacity.jdnd.course3.critter.repository.PetScheduleRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private final Logger logger = LoggerFactory.getLogger(ScheduleService.class.getSimpleName());

    private final PetScheduleRepository petScheduleRepository;
    private final EmployeeScheduleRepository employeeScheduleRepository;
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(PetScheduleRepository petScheduleRepository, EmployeeScheduleRepository employeeScheduleRepository, ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository, CustomerRepository customerRepository) {
        this.petScheduleRepository = petScheduleRepository;
        this.employeeScheduleRepository = employeeScheduleRepository;
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }


    public Schedule save(Schedule schedule) {
        Schedule savedSchedule = scheduleRepository.save(schedule);
        savedSchedule.getEmployeeSchedules().forEach(empSchedule -> {
            if (!employeeRepository.findById(empSchedule.getEmployeeId()).isPresent())
                throw new EmployeeNotFoundException();
            empSchedule.setSchedule(savedSchedule);
            employeeScheduleRepository.save(empSchedule);
        });

        savedSchedule.getPetSchedules().forEach(petSchedule -> {
            if (!petRepository.findById(petSchedule.getPetId()).isPresent())
                throw new PetNotFoundException("Pet with id: " + petSchedule.getPetId() + " not found");
            petSchedule.setSchedule(savedSchedule);
            petScheduleRepository.save(petSchedule);
        });
        return savedSchedule;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        List<PetSchedule> petSchedules = petScheduleRepository.findAllByPetId(petId);
        List<Schedule> schedules = new ArrayList<>();
        petSchedules.forEach(petSchedule -> schedules.add(petSchedule.getSchedule()));
        return schedules;
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        List<EmployeeSchedule> employeeSchedules = employeeScheduleRepository.findAllByEmployeeId(employeeId);
        List<Schedule> schedules = new ArrayList<>();
        employeeSchedules.forEach(employeeSchedule -> schedules.add(employeeSchedule.getSchedule()));
        return schedules;
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        List<Long> petIds = petRepository.findByOwnerId(customerId)
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        List<PetSchedule> petSchedules = petScheduleRepository.findAllByPetIdIn(petIds);
        List<Schedule> schedules = new ArrayList<>();
        petSchedules.forEach(petSchedule -> schedules.add(petSchedule.getSchedule()));
        return schedules;
    }
}
