package com.udacity.jdnd.course3.critter.schedule.dto.mapper;

import com.udacity.jdnd.course3.critter.domain.EmployeeSchedule;
import com.udacity.jdnd.course3.critter.domain.PetSchedule;
import com.udacity.jdnd.course3.critter.domain.Schedule;
import com.udacity.jdnd.course3.critter.schedule.exception.ScheduleException;
import com.udacity.jdnd.course3.critter.schedule.dto.ScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {
    private Logger logger = LoggerFactory.getLogger(ScheduleMapper.class.getSimpleName());

    public Schedule mapScheduleDTOToSchedule(ScheduleDTO scheduleDTO) throws Exception {
        Schedule schedule = new Schedule();
        List<EmployeeSchedule> employeeSchedules = new ArrayList<>();
        List<PetSchedule> petSchedules = new ArrayList<>();

        if (scheduleDTO.getEmployeeIds() == null || scheduleDTO.getEmployeeIds().isEmpty())
            throw new ScheduleException("Schedule must have at least one Employee");

        if (scheduleDTO.getPetIds() == null || scheduleDTO.getPetIds().isEmpty())
            throw new ScheduleException("Schedule must have at least one Pet");

        scheduleDTO.getEmployeeIds().forEach(empId -> employeeSchedules.add(new EmployeeSchedule(empId)));

        scheduleDTO.getPetIds().forEach(petId -> petSchedules.add(new PetSchedule(petId)));

        schedule.setEmployeeSchedules(employeeSchedules);
        schedule.setPetSchedules(petSchedules);
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }

    public List<ScheduleDTO> mapSchedulesToScheduleDTOs(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> {

            List<Long> empIds = schedule.getEmployeeSchedules()
                    .stream()
                    .map(EmployeeSchedule::getEmployeeId)
                    .collect(Collectors.toList());

            List<Long> petIds = schedule.getPetSchedules()
                    .stream()
                    .map(PetSchedule::getPetId)
                    .collect(Collectors.toList());

            scheduleDTOS.add(new ScheduleDTO(
                    schedule.getId(),
                    empIds,
                    petIds,
                    schedule.getDate(),
                    schedule.getActivities())
            );
        });
        return scheduleDTOS;
    }

    public ScheduleDTO mapScheduleTOScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        if (schedule.getEmployeeSchedules() != null &&
                !schedule.getEmployeeSchedules().isEmpty()) {
            scheduleDTO.setEmployeeIds(
                    schedule.getEmployeeSchedules()
                            .stream()
                            .map(EmployeeSchedule::getEmployeeId)
                            .collect(Collectors.toList())
            );
        }
        if (schedule.getPetSchedules() != null &&
                !schedule.getPetSchedules().isEmpty()) {
            scheduleDTO.setPetIds(
                    schedule.getPetSchedules()
                            .stream()
                            .map(PetSchedule::getPetId)
                            .collect(Collectors.toList()));

        }
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        return scheduleDTO;
    }

}
