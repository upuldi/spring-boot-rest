package com.upuldi.api.airport.service;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;
import com.upuldi.integration.service.AirportService;
import com.upuldi.integration.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This would be the main service class for {@link com.upuldi.api.airport.controller.AirportController}
 * This class depend on {@link com.upuldi.integration.service.AirportService} to get cached data
 *
 * Created by udoluweera on 3/26/17.
 */
@Service
public class AirportRestServiceImpl implements AirportRestService {

    @Autowired
    private AirportService airportService;

    @Autowired
    private CountryService countryService;

    @Override
    public Airport getAirportByCode(String airportCode) {
        List<Airport> cachedAirports = airportService.getAllCachedAirports();
        List<Airport> result = cachedAirports.stream().filter(a -> a.getCode().equalsIgnoreCase(airportCode)).collect(Collectors.toList());
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            throw new AirportNotFoundException(airportCode);
        }
    }

    @Override
    public List<Airport> getAirportsByCountry(String code) {
        Country country = countryService.getCountryByCode(code.toUpperCase());
        if(null != country) {
            return airportService.getAirportByCountry(country);
        } else {
            throw new CountryNotFoundException(code);
        }
    }

    @Override
    public List<Airport> getInternationalAirports() {
        return airportService.getInternationalAirports();
    }

    @Override
    public List<Airport> getDomesticAirports() {
        return airportService.getDomesticAirports();
    }
}
