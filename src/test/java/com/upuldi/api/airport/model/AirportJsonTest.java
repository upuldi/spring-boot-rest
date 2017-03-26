package com.upuldi.api.airport.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests class for {@link Airport}
 *
 * Created by udoluweera on 3/26/17.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class AirportJsonTest {

    public static final String AIRPORT_CODE = "BZD";
    public static final String AIRPORT_NAME = "Balranald";
    public static final String LATITUDE = "-34.616665";
    public static final String LONGITUDE = "143.61667";
    public static final String CURRENCY_CODE = "AUD";
    public static final String TIMEZONE = "Australia/Sydney";
    public static final String COUNTRY_CODE = "AU";
    public static final String COUNTRY_NAME = "Australia";


    @Autowired
    private JacksonTester<Airport> jacksonTester;

    private Airport airport;
    private Location location;
    private Country country;

    @Before
    public void setUp() throws Exception {

        airport = new Airport();
        airport.setCode(AIRPORT_CODE);
        airport.setName(AIRPORT_NAME);
        airport.setInternational(true);
        airport.setRegional(false);

        location = new Location(LATITUDE,LONGITUDE);
        airport.setLocation(location);

        airport.setCurrencyCode(CURRENCY_CODE);
        airport.setTimezone(TIMEZONE);

        country = new Country(COUNTRY_CODE,COUNTRY_NAME);
        airport.setCountry(country);
    }

    @Test
    public void airportShouldSerializedIntoCorrectJson() throws Exception {
        assertThat(jacksonTester.write(airport)).isEqualToJson("airport.json");
    }

    @Test
    public void serializedJsonShouldContainProperties() throws Exception {

        assertThat(jacksonTester.write(airport)).hasJsonPathStringValue("@.code");
        assertThat(jacksonTester.write(airport)).hasJsonPathStringValue("@.display_name");
        assertThat(jacksonTester.write(airport)).hasJsonPathBooleanValue("@.international_airport");
        assertThat(jacksonTester.write(airport)).hasJsonPathBooleanValue("@.regional_airport");

        assertThat(jacksonTester.write(airport)).hasJsonPathValue("@.location", location);

        assertThat(jacksonTester.write(airport)).hasJsonPathStringValue("@.currency_code");
        assertThat(jacksonTester.write(airport)).hasJsonPathStringValue("@.timezone");

        assertThat(jacksonTester.write(airport)).hasJsonPathValue("@.country", country);
    }

    @Test
    public void serializedJsonShouldHaveCorrectValues() throws Exception {

        assertThat(jacksonTester.write(airport)).extractingJsonPathStringValue("@.code").isEqualTo(AIRPORT_CODE);
        assertThat(jacksonTester.write(airport)).extractingJsonPathStringValue("@.display_name").isEqualTo(AIRPORT_NAME);
        assertThat(jacksonTester.write(airport)).extractingJsonPathBooleanValue("@.international_airport").isEqualTo(true);
        assertThat(jacksonTester.write(airport)).extractingJsonPathBooleanValue("@.regional_airport").isEqualTo(false);
        assertThat(jacksonTester.write(airport)).extractingJsonPathStringValue("@.currency_code").isEqualTo(CURRENCY_CODE);
        assertThat(jacksonTester.write(airport)).extractingJsonPathStringValue("@.timezone").isEqualTo(TIMEZONE);
    }

}