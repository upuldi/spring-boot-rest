package com.upuldi.core.config;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.api.airport.model.Country;
import com.upuldi.integration.service.AirportsIntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.upuldi.core.util.AppConstants.*;

/**
 * A Simple class is used to schedule the ehCache.
 * Cache wil be refreshed in every 3 minutes to prevent serving invalidated data.
 *
 * Created by udoluweera on 3/26/17.
 */
@Component
public class AirportCacheScheduler {

    @Autowired
    private AirportsIntegrationService airportsIntegrationService;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {
        loadAirportsCache();
    }
    private static final Logger logger = LoggerFactory.getLogger(AirportCacheScheduler.class);


    public void loadAirportsCache() {

        logger.info("Loading airport cache....");

        List<Airport> airportList = airportsIntegrationService.getAirportsFromRemote();
        cacheManager.getCache(AIRPORT_CACHE).put(AIRPORT_ALL_CACHE,airportList);

        Map<Country, List<Airport>> airportsByCountry = airportList.stream().collect(Collectors.groupingBy(a -> a.getCountry()));
        cacheManager.getCache(AIRPORT_CACHE).put(BY_COUNTRY_CACHE,airportsByCountry);

        Map<String, Country> countryByCode = airportsByCountry.keySet().stream().collect(Collectors.toMap(c -> c.getCode(), c -> c));
        cacheManager.getCache(AIRPORT_CACHE).put(COUNTRIES_CACHE,countryByCode);

        List<Airport> internationalAirports = airportList.stream().filter(Airport::isInternational).collect(Collectors.toList());
        cacheManager.getCache(AIRPORT_CACHE).put(INTERNATIONAL_CACHE,internationalAirports);

        List<Airport> domesticAirports = airportList.stream().filter(Airport::isRegional).collect(Collectors.toList());
        cacheManager.getCache(AIRPORT_CACHE).put(DOMESTIC_CACHE,domesticAirports);

        logger.info("cache loading completed");
    }

    @Scheduled(fixedRate = 3L * 60L * 1000L)
    public void automaticCacheRefresh(){
        evictCache();
        loadAirportsCache();
    }

    @CacheEvict(AIRPORT_CACHE)
    public void evictCache() {
        logger.info("Evicting cache.....");

    }

}
