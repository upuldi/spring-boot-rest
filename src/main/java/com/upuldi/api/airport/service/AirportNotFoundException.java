package com.upuldi.api.airport.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by udoluweera on 3/26/17.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AirportNotFoundException extends RuntimeException {

    private String airportCode;

    public AirportNotFoundException(String airportCode) {
        this(airportCode, null);
    }

    public AirportNotFoundException(String airportCode,Throwable cause) {
        super("Unable to find Airport for given code " + airportCode, cause);
        this.airportCode = airportCode;
    }

    public String getAirportCode() {
        return airportCode;
    }
}
