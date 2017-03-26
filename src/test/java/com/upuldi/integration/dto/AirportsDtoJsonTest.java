package com.upuldi.integration.dto;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;
import com.upuldi.api.airport.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for AirportsDto
 *
 *
 * Created by udoluweera on 3/26/17.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class AirportsDtoJsonTest {

    public static final String AIRPORT_CODE = "BZD";
    public static final String AIRPORT_NAME = "Balranald";
    public static final String LATITUDE = "-34.616665";
    public static final String LONGITUDE = "143.61667";
    public static final String CURRENCY_CODE = "AUD";
    public static final String TIMEZONE = "Australia/Sydney";
    public static final String COUNTRY_CODE = "AU";
    public static final String COUNTRY_NAME = "Australia";

    @Autowired
    private JacksonTester<AirportsDto> jacksonTester;

    private AirportsDto airportsDto;
    private Airport airport;
    private Location location;
    private Country country;

    @Before
    public void setUp() throws Exception {

        airport = new Airport();
        airport.setCode(AIRPORT_CODE);
        airport.setName(AIRPORT_NAME);
        airport.setInternational(false);
        airport.setRegional(false);

        location = new Location(LATITUDE,LONGITUDE);
        airport.setLocation(location);

        airport.setCurrencyCode(CURRENCY_CODE);
        airport.setTimezone(TIMEZONE);

        country = new Country(COUNTRY_CODE,COUNTRY_NAME);
        airport.setCountry(country);

        airportsDto = new AirportsDto();
        airportsDto.setAirportList(Arrays.asList(airport));
    }

    @Test
    public void airportShouldSerializedIntoCorrectJson() throws Exception {
        assertThat(jacksonTester.write(airportsDto)).isEqualToJson("airportsList.json");
    }



}