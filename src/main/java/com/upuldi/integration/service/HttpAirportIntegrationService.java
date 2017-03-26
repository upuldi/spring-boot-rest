package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Airport;
import com.upuldi.integration.dto.AirportsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * An integration service class responsible for integration with an external system over HTTP.
 * {@link com.upuldi.core.config.AirportCacheScheduler} use this service to load the cache.
 *
 * Created by udoluweera on 3/26/17.
 */
@Service
public class HttpAirportIntegrationService implements AirportsIntegrationService {

    private RestTemplate restTemplate;

    @Value("${remote.service.endpoint}")
    private String externalApiEndpoint;

    @Override
    public List<Airport> getAirportsFromRemote() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<AirportsDto> response = restTemplate.exchange(externalApiEndpoint,
                HttpMethod.GET, entity, new ParameterizedTypeReference<AirportsDto>() {
                });
        AirportsDto airports = response.getBody();
        return airports.getAirportList();
    }


    public HttpAirportIntegrationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

}
