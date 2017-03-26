package com.upuldi.api.airport.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by udoluweera on 3/26/17.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CountryNotFoundException extends RuntimeException {

    private String countryCode;

    public CountryNotFoundException(String countryCode) {
        this(countryCode, null);
    }

    public CountryNotFoundException(String countryCode, Throwable cause) {
        super("Unable to find Country for given code " + countryCode, cause);
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }
}