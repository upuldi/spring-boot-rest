package com.upuldi.api.airport.service;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;
import com.upuldi.api.airport.model.Location;
import com.upuldi.integration.service.AirportService;
import com.upuldi.integration.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link AirportRestServiceImpl}
 *
 * Created by udoluweera on 3/26/17.
 */
@RunWith(SpringRunner.class)
public class AirportRestServiceImplTest {

    public static final String AIR_PORT_CODE = "BZD";
    public static final String NAME = "Balranald";
    public static final String CURRENCY_CODE = "AUD";
    public static final String TIMEZONE = "Australia/Sydney";
    public static final String COUNTRY_CODE = "AU";
    public static final String COUNTRY_NAME = "Australia";
    public static final String LATITUDE = "-34.616665";
    public static final String LONGITUDE = "143.61667";

    @Mock
    private AirportService airportService;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private AirportRestServiceImpl airportRestService;

    private List<Airport> airportList;
    private Country country;

    @Captor
    private ArgumentCaptor<Country> countryArgumentCaptor;

    @Before
    public void setUp() throws Exception {

        Airport airport = new Airport();
        airport.setLocation(new Location(LATITUDE, LONGITUDE));
        airport.setCode(AIR_PORT_CODE);
        airport.setName(NAME);
        airport.setInternational(false);
        airport.setRegional(false);
        airport.setCurrencyCode(CURRENCY_CODE);
        airport.setTimezone(TIMEZONE);

        country = new Country(COUNTRY_CODE, COUNTRY_NAME);
        airport.setCountry(country);

        airportList = Arrays.asList(airport);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAirportByCodeShouldReturnAirport() throws Exception {

        given(airportService.getAllCachedAirports()).willReturn(airportList);

        Airport airportReturned = airportRestService.getAirportByCode(AIR_PORT_CODE);
        assertNotNull(airportReturned);

        validateReturnObject(airportReturned);
    }

    @Test(expected = AirportNotFoundException.class)
    public void shouldThrowNotFoundIfNothingReturned() {
        given(airportService.getAllCachedAirports()).willReturn(airportList);
        airportRestService.getAirportByCode("123");
    }

    @Test
    public void getAirportsByCountry() throws Exception {

        given(countryService.getCountryByCode(any(String.class))).willReturn(country);
        given(airportService.getAirportByCountry(countryArgumentCaptor.capture())).willReturn(airportList);

        List<Airport> airports = airportRestService.getAirportsByCountry(COUNTRY_CODE);

        verify(countryService,times(1)).getCountryByCode(COUNTRY_CODE);
        assertThat(COUNTRY_CODE,is(countryArgumentCaptor.getValue().getCode()));

        validateReturnObject(airports.get(0));
    }

    @Test(expected = CountryNotFoundException.class)
    public void shouldThrowNotFoundforCountry() throws Exception {
        given(countryService.getCountryByCode(any(String.class))).willReturn(null);
        airportRestService.getAirportsByCountry(COUNTRY_CODE);
    }

    @Test
    public void getInternationalAirports() throws Exception {
        given(airportService.getInternationalAirports()).willReturn(airportList);
        List<Airport> list = airportRestService.getInternationalAirports();
        assertNotNull(list);
        validateReturnObject(list.get(0));
    }

    @Test
    public void getDomesticAirports() throws Exception {
        given(airportService.getDomesticAirports()).willReturn(airportList);
        List<Airport> list = airportRestService.getDomesticAirports();
        assertNotNull(list);
        validateReturnObject(list.get(0));
    }

    private void validateReturnObject(Airport airportReturned) {
        assertThat(airportReturned.getCode(), is(AIR_PORT_CODE));
        assertThat(airportReturned.getName(), is(NAME));
        assertThat(airportReturned.getLocation().getLatitude(), is(LATITUDE));
        assertThat(airportReturned.getLocation().getLongitude(), is(LONGITUDE));
        assertThat(airportReturned.getCountry().getName(), is(COUNTRY_NAME));
        assertThat(airportReturned.getCountry().getCode(), is(COUNTRY_CODE));
        assertThat(airportReturned.getCurrencyCode(), is(CURRENCY_CODE));
        assertThat(airportReturned.getTimezone(), is(TIMEZONE));
    }

}