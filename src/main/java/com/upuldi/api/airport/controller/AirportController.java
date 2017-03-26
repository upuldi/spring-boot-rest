package com.upuldi.api.airport.controller;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.service.AirportRestService;
import com.upuldi.core.api.version.PublicApiVersion1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.upuldi.core.util.URLConstants.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * RestController For Airport Services
 *
 * Here error handling and service calls has been done in a simplistic manner to avoid complexity.
 * Service calls have not been encapsulated into command (pattern) like interfaces to provide a common
 * response entity and errors codes associated. However it would be encouraged in an actual enterprise
 * app development
 *
 * Created by udoluweera on 3/25/17.
 */
@PublicApiVersion1
public class AirportController {

    @Autowired
    private AirportRestService airportRestService;

    public AirportController(AirportRestService airportRestService) {
        this.airportRestService = airportRestService;
    }

    @GetMapping(path = AIRPORT_BY_CODE)
    public ResponseEntity<Airport> getAirportByCode(@PathVariable(CODE) String airportCode) {

        Airport airport = airportRestService.getAirportByCode(airportCode);
        setLinks(airport);
        return new ResponseEntity<Airport>(airport, HttpStatus.OK);
    }

    @GetMapping(path = AIRPORTS_BY_COUNTRY)
    public ResponseEntity<List<Airport>> getAirportsByCountry(@PathVariable(CODE) String countryCode) {

        List<Airport> airports = airportRestService.getAirportsByCountry(countryCode);
        airports.forEach(airport -> setLinks(airport));
        return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
    }

    @GetMapping(path = INTERNATIONAL_AIRPORTS)
    public ResponseEntity<List<Airport>> getInternationalAirports() {
        List<Airport> airports = airportRestService.getInternationalAirports();
        airports.forEach(airport -> setLinks(airport));
        return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
    }

    @GetMapping(path = DOMESTIC_AIRPORTS)
    public ResponseEntity<List<Airport>> getDomesticAirports() {
        List<Airport> airports = airportRestService.getDomesticAirports();
        airports.forEach(airport -> setLinks(airport));
        return new ResponseEntity<List<Airport>>(airports, HttpStatus.OK);
    }

    private void setLinks(Airport airport) {
        airport.removeLinks();
        airport.add(linkTo(methodOn(AirportController.class).getAirportByCode(airport.getCode())).withSelfRel());
    }

}
