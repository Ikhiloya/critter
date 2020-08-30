package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.Schedule;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
//TODO: extract mappers to mapper classes
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final Logger logger = LoggerFactory.getLogger(ScheduleController.class.getSimpleName());

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    public ScheduleController(ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) throws Exception {
        logger.info("request to create a schedule");
        Schedule schedule = scheduleService.save(scheduleMapper.mapScheduleDTOToSchedule(scheduleDTO));
        return scheduleMapper.mapScheduleTOScheduleDTO(schedule);
    }


    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        logger.info("request to get all schedules");
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return scheduleMapper.mapSchedulesToScheduleDTOs(schedules);
    }


    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        logger.info("request to get schedules for a pet");
        List<Schedule> petSchedules = scheduleService.getScheduleForPet(petId);
        return scheduleMapper.mapSchedulesToScheduleDTOs(petSchedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        logger.info("request to get schedules for a employee");
        List<Schedule> employeeSchedules = scheduleService.getScheduleForEmployee(employeeId);
        return scheduleMapper.mapSchedulesToScheduleDTOs(employeeSchedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        logger.info("request to get schedules for a customer");
        List<Schedule> customerSchedules = scheduleService.getScheduleForCustomer(customerId);
        return scheduleMapper.mapSchedulesToScheduleDTOs(customerSchedules);

    }
}
