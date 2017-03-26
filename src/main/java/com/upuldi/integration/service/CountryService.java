package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Country;

/**
 * Created by udoluweera on 3/26/17.
 */
public interface CountryService {
    Country getCountryByCode(String code);
}
