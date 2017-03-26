package com.upuldi.api.airport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.upuldi.core.api.model.ApiDto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by udoluweera on 3/25/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport extends ApiDto implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("display_name")
    private String name;

    @JsonProperty("international_airport")
    private boolean international;

    @JsonProperty("regional_airport")
    private boolean regional;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("country")
    private Country country;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInternational() {
        return international;
    }

    public boolean getInternational() {
        return international;
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public boolean isRegional() {
        return regional;
    }

    public boolean getRegional() {
        return regional;
    }

    public void setRegional(boolean regional) {
        this.regional = regional;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Airport airport = (Airport) o;
        return international == airport.international &&
                regional == airport.regional &&
                Objects.equals(code, airport.code) &&
                Objects.equals(name, airport.name) &&
                Objects.equals(location, airport.location) &&
                Objects.equals(currencyCode, airport.currencyCode) &&
                Objects.equals(timezone, airport.timezone) &&
                Objects.equals(country, airport.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, name, international, regional, location, currencyCode, timezone, country);
    }
}
