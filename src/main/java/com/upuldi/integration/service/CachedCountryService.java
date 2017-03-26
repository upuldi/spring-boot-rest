package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.upuldi.core.util.AppConstants.AIRPORT_CACHE;
import static com.upuldi.core.util.AppConstants.COUNTRIES_CACHE;

/**
 * Cached based service implementation for {@link CountryService}
 *
 * Created by udoluweera on 3/26/17.
 */
@Service
public class CachedCountryService implements CountryService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Country getCountryByCode(String code) {
        Cache.ValueWrapper valueWrapper = cacheManager.getCache(AIRPORT_CACHE).get(COUNTRIES_CACHE);
        Map<String, Country> countryByCode = (Map<String, Country>) valueWrapper.get();

        if (null != countryByCode && countryByCode.size() > 0) {
            return countryByCode.get(code);
        }
        return null;
    }
}
