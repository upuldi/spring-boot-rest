package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.upuldi.core.util.AppConstants.AIRPORT_CACHE;
import static com.upuldi.core.util.AppConstants.COUNTRIES_CACHE;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 *  Test class for {@link CachedCountryService}
 *
 * Created by udoluweera on 3/26/17.
 */
@RunWith(SpringRunner.class)
public class CachedCountryServiceTest {

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private CachedCountryService countryService;

    @Mock
    private Cache cache;

    @Mock
    private Cache.ValueWrapper valueWrapper;

    private Map<String, Country> countryMap;
    private Country country;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        country = new Country("AU", "Australia");
        countryMap = new HashMap<>();
        countryMap.put("AU",country);
    }


    @Test
    public void getCountryByCode() throws Exception {
        given(cacheManager.getCache(AIRPORT_CACHE)).willReturn(cache);
        given(cache.get(COUNTRIES_CACHE)).willReturn(valueWrapper);
        given(valueWrapper.get()).willReturn(countryMap);

        Country country = countryService.getCountryByCode("AU");
        assertNotNull(country);
        assertEquals(this.country,country);

    }

}