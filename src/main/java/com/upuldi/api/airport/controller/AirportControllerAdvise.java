package com.upuldi.api.airport.controller;

import com.upuldi.api.airport.service.AirportNotFoundException;
import com.upuldi.api.airport.service.CountryNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Optional;

/**
 * Adviser for handling errors for {@link AirportController}
 *
 * Created by udoluweera on 3/26/17.
 */
@ControllerAdvice
@RequestMapping(produces = "application/vnd.error")
@ResponseBody
public class AirportControllerAdvise {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(AirportNotFoundException.class)
    public VndErrors airportNotFoundException(AirportNotFoundException ex) {
        return composeError(ex, ex.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CountryNotFoundException.class)
    public VndErrors countryNotFoundException(CountryNotFoundException ex) {
        return composeError(ex, ex.getLocalizedMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public VndErrors requestHandlingNoHandlerFound() {
        return composeError(new RuntimeException("not supported"), "service not available");
    }

    private <E extends Exception> VndErrors composeError(E e, String logref) {
        String msg = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
        return new VndErrors(logref, msg);
    }
}
