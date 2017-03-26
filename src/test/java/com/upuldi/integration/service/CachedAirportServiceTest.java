package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;
import com.upuldi.api.airport.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.upuldi.core.util.AppConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

/**
 * Test class for {@link CachedAirportService}
 *
 * Created by udoluweera on 3/26/17.
 */
@RunWith(SpringRunner.class)
public class CachedAirportServiceTest {

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private CachedAirportService cachedAirportService;

    @Captor
    private ArgumentCaptor<Country> countryArgumentCaptor;

    @Mock
    private Cache cache;

    @Mock
    private Cache.ValueWrapper valueWrapper;

    private List<Airport> airportList;
    private Airport airport;
    private Country country;
    private Map<Country, List<Airport>> airportsByCountry;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        airport = new Airport();
        airport.setLocation(new Location("-34.616665", "143.61667"));
        airport.setCode("BZD");
        airport.setName("Balranald");
        airport.setInternational(false);
        airport.setRegional(false);
        airport.setCurrencyCode( "AUD");
        airport.setTimezone("Australia/Sydney");

        country = new Country("AU", "Australia");
        airport.setCountry(country);

        airportList = Arrays.asList(airport);
        airportsByCountry = new HashMap<>();
        airportsByCountry.put(country,airportList);
    }

    @Test
    public void getAllCachedAirports() throws Exception {

        given(cacheManager.getCache(AIRPORT_CACHE)).willReturn(cache);
        given(cache.get(AIRPORT_ALL_CACHE)).willReturn(valueWrapper);
        given(valueWrapper.get()).willReturn(airportList);

        List<Airport> airports = cachedAirportService.getAllCachedAirports();
        assertNotNull(airports);
        assertEquals(this.airportList,airports);
    }

    @Test
    public void getAirportByCountry() throws Exception {
        given(cacheManager.getCache(AIRPORT_CACHE)).willReturn(cache);
        given(cache.get(BY_COUNTRY_CACHE)).willReturn(valueWrapper);
        given(valueWrapper.get()).willReturn(airportsByCountry);

        List<Airport> airports = cachedAirportService.getAirportByCountry(country);
        assertNotNull(airports);
        assertEquals(this.airportList,airports);
    }

    @Test
    public void getInternationalAirports() throws Exception {

        given(cacheManager.getCache(AIRPORT_CACHE)).willReturn(cache);
        given(cache.get(INTERNATIONAL_CACHE)).willReturn(valueWrapper);
        given(valueWrapper.get()).willReturn(airportList);

        List<Airport> airports = cachedAirportService.getInternationalAirports();
        assertNotNull(airports);
        assertEquals(this.airportList,airports);
    }

    @Test
    public void getDomesticAirports() throws Exception {

        given(cacheManager.getCache(AIRPORT_CACHE)).willReturn(cache);
        given(cache.get(DOMESTIC_CACHE)).willReturn(valueWrapper);
        given(valueWrapper.get()).willReturn(airportList);

        List<Airport> airports = cachedAirportService.getDomesticAirports();
        assertNotNull(airports);
        assertEquals(this.airportList,airports);
    }

}