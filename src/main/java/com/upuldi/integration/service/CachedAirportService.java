package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.upuldi.core.util.AppConstants.*;

/**
 * Cache based service implementation for {@link AirportService}
 *
 *
 * Created by udoluweera on 3/26/17.
 */
@Service
public class CachedAirportService implements AirportService {

    @Autowired
    private CacheManager cacheManager;


    @Override
    public List<Airport> getAllCachedAirports() {
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(AIRPORT_CACHE).get(AIRPORT_ALL_CACHE);
        List<Airport> allAirports = (List<Airport>) valueWrapper.get();
        return allAirports;
    }

    @Override
    public List<Airport> getAirportByCountry(Country country) {
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(AIRPORT_CACHE).get(BY_COUNTRY_CACHE);
        Map<Country, List<Airport>> airportsByCountry = (Map<Country, List<Airport>>) valueWrapper.get();
        return airportsByCountry.get(country);
    }

    @Override
    public List<Airport> getInternationalAirports() {
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(AIRPORT_CACHE).get(INTERNATIONAL_CACHE);
        return (List<Airport>) valueWrapper.get();
    }

    @Override
    public List<Airport> getDomesticAirports() {
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(AIRPORT_CACHE).get(DOMESTIC_CACHE);
        return (List<Airport>) valueWrapper.get();
    }
}
