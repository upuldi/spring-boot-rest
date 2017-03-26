package com.upuldi.api.airport.service;

import com.upuldi.api.airport.model.Airport;

import java.util.List;

/**
 * Created by udoluweera on 3/26/17.
 */

public interface AirportRestService {

    Airport getAirportByCode(String airportCode);

    List<Airport> getAirportsByCountry(String code);

    List<Airport> getInternationalAirports();

    List<Airport> getDomesticAirports();
}
