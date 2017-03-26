package com.upuldi.integration.service;

import com.upuldi.api.airport.model.Airport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Test class for {@link HttpAirportIntegrationService}
 *
 * Created by udoluweera on 3/26/17.
 */
@RunWith(SpringRunner.class)
@RestClientTest({ HttpAirportIntegrationService.class})
@TestPropertySource(properties = "remote.service.endpoint=http://remotehost.com/")
public class HttpAirportIntegrationServiceTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private HttpAirportIntegrationService service;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Test
    public void getVehicleDetailsWhenResultIsSuccessShouldReturnDetails()
            throws Exception {

        this.server.expect(requestTo("http://remotehost.com/")).andRespond(withSuccess(getClassPathResource("mockRemoteResponse.json"),
                        MediaType.APPLICATION_JSON));
        List<Airport> airports = service.getAirportsFromRemote();
        assertNotNull(airports);
    }

    private ClassPathResource getClassPathResource(String path) {
        return new ClassPathResource(path, getClass());
    }

}