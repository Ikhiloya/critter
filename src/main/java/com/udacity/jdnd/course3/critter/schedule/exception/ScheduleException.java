package com.udacity.jdnd.course3.critter.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ScheduleException extends Exception {
    public ScheduleException() {
    }

    public ScheduleException(String message) {
        super(message);
    }
}
