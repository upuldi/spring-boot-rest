package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Airport;

import java.util.List;

/**
 * Main purpose of this interface is to provide abstract layer for IntegrationServices
 *
 * Created by udoluweera on 3/26/17.
 */
public interface AirportsIntegrationService {
    List<Airport> getAirportsFromRemote();
}
