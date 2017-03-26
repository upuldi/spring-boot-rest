package com.upuldi.api.airport.controller;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;
import com.upuldi.api.airport.model.Location;
import com.upuldi.api.airport.service.AirportNotFoundException;
import com.upuldi.api.airport.service.AirportRestServiceImpl;
import com.upuldi.api.airport.service.CountryNotFoundException;
import com.upuldi.core.util.URLConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.upuldi.core.util.URLConstants.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests class for {@link AirportController}.
 *
 * Created by udoluweera on 3/25/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AirportControllerTest {

    public static final String AIR_PORT_CODE = "BZD";
    public static final String NAME = "Balranald";
    public static final String CURRENCY_CODE = "AUD";
    public static final String TIMEZONE = "Australia/Sydney";
    public static final String COUNTRY_CODE = "AU";
    public static final String COUNTRY_NAME = "Australia";
    public static final String LATITUDE = "-34.616665";
    public static final String LONGITUDE = "143.61667";

    @InjectMocks
    private AirportController airportController;
    private MockMvc mockMvc;

    @Mock
    private AirportRestServiceImpl airportRestService;

    private Airport airport;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();

        airport = new Airport();
        airport.setLocation(new Location(LATITUDE, LONGITUDE));
        airport.setCode(AIR_PORT_CODE);
        airport.setName(NAME);
        airport.setInternational(false);
        airport.setRegional(false);
        airport.setCurrencyCode(CURRENCY_CODE);
        airport.setTimezone(TIMEZONE);
        airport.setCountry(new Country(COUNTRY_CODE, COUNTRY_NAME));
    }

    @Test
    public void getAirportsShouldBePublic() throws Exception {

        given(airportRestService.getAirportByCode(AIR_PORT_CODE)).willReturn(airport);

        mockMvc.perform(get(withPathVariable(AIRPORT_BY_CODE, URLConstants.CODE, AIR_PORT_CODE)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(CURRENT_SECURE_VERSION + withPathVariable(AIRPORT_BY_CODE, URLConstants.CODE, AIR_PORT_CODE)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void currentGetAirportsShouldBeVersionOne() throws Exception {

        given(airportRestService.getAirportByCode(AIR_PORT_CODE)).willReturn(airport);

        mockMvc.perform(get(withPathVariable(AIRPORT_BY_CODE, URLConstants.CODE, AIR_PORT_CODE)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/public/api/v2.0/" + AIRPORTS).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAirportByCodeShouldReturnAirport() throws Exception {

        given(airportRestService.getAirportByCode(AIR_PORT_CODE)).willReturn(airport);

        ResultActions resultActions = mockMvc.perform(get(withPathVariable(AIRPORT_BY_CODE,
                URLConstants.CODE, AIR_PORT_CODE)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        checkAirportJson(resultActions, airport);
    }

    @Test
    public void shouldHandleAirportNotFound() throws Exception {

        given(airportRestService.getAirportByCode(AIR_PORT_CODE)).willThrow(AirportNotFoundException.class);

        mockMvc.perform(get(withPathVariable(AIRPORT_BY_CODE,
                URLConstants.CODE, AIR_PORT_CODE)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAirportByCountryShouldReturnAListOfAirports() throws Exception {

        given(airportRestService.getAirportsByCountry(COUNTRY_CODE)).willReturn(Arrays.asList(airport));

        ResultActions resultActions = mockMvc.perform(get(withPathVariable(AIRPORTS_BY_COUNTRY,
                URLConstants.CODE, COUNTRY_CODE)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        checkFirstAirportInListResponse(resultActions, airport);
    }

    @Test
    public void shouldHandleCountryNotFound() throws Exception {
        given(airportRestService.getAirportsByCountry(COUNTRY_CODE)).willThrow(CountryNotFoundException.class);
        mockMvc.perform(get(withPathVariable(AIRPORTS_BY_COUNTRY,
                URLConstants.CODE, COUNTRY_CODE)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getInternationalAirportsShouldReturnListOfInternationalAirports() throws Exception {

        airport.setInternational(true);
        given(airportRestService.getInternationalAirports()).willReturn(Arrays.asList(airport));

        ResultActions resultActions = mockMvc.perform(get(withVersion(INTERNATIONAL_AIRPORTS)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        checkFirstAirportInListResponse(resultActions, airport);
    }

    @Test
    public void getDomesticAirportsShouldReturnListOfDomesticAirports() throws Exception {

        airport.setRegional(true);
        given(airportRestService.getDomesticAirports()).willReturn(Arrays.asList(airport));

        ResultActions resultActions = mockMvc.perform(get(withVersion(DOMESTIC_AIRPORTS)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        checkFirstAirportInListResponse(resultActions, airport);
    }


    private void checkAirportJson(ResultActions resultActions, Airport airport) throws Exception {
        resultActions.andExpect(jsonPath("@.code").value(airport.getCode())).
                andExpect(jsonPath("@.display_name").value(airport.getName())).
                andExpect(jsonPath("@.international_airport").value(airport.isInternational())).
                andExpect(jsonPath("@.regional_airport").value(airport.isRegional())).
                andExpect(jsonPath("@.location.latitude").value(airport.getLocation().getLatitude())).
                andExpect(jsonPath("@.location.longitude").value(airport.getLocation().getLongitude())).
                andExpect(jsonPath("@.currency_code").value(airport.getCurrencyCode())).
                andExpect(jsonPath("@.timezone").value(airport.getTimezone())).
                andExpect(jsonPath("@.country.code").value(airport.getCountry().getCode())).
                andExpect(jsonPath("@.country.display_name").value(airport.getCountry().getName()));
    }

    private void checkFirstAirportInListResponse(ResultActions resultActions, Airport airport) throws Exception {
        resultActions.andExpect(jsonPath("@[:1].code").value(airport.getCode())).
                andExpect(jsonPath("@[:1].display_name").value(airport.getName())).
                andExpect(jsonPath("@[:1].international_airport").value(airport.isInternational())).
                andExpect(jsonPath("@[:1].regional_airport").value(airport.isRegional())).
                andExpect(jsonPath("@[:1].currency_code").value(airport.getCurrencyCode())).
                andExpect(jsonPath("@[:1].timezone").value(airport.getTimezone())).
                andExpect(jsonPath("@[:1].country.code").value(airport.getCountry().getCode())).
                andExpect(jsonPath("@[:1].country.display_name").value(airport.getCountry().getName()));
    }


    private String withVersion(String name) {
        return CURRENT_PUBLIC_VERSION + name;
    }

    private String withPathVariable(String path, String variable, String value) {
        return withVersion(path.replace("{" + variable + "}", value));
    }


}