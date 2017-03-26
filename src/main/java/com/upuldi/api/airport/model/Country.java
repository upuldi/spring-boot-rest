package com.upuldi.api.airport.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by udoluweera on 3/26/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Country implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("display_name")
    private String name;



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(code, country.code) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Country() {}
}
