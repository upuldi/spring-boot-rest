package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;

import java.util.List;

/**
 * Created by udoluweera on 3/26/17.
 */
public interface AirportService {

    List<Airport> getAllCachedAirports();
    List<Airport> getAirportByCountry(Country country);
    List<Airport> getInternationalAirports();
    List<Airport> getDomesticAirports();
}
