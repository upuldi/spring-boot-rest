package com.upuldi.core.util;

/**
 * Created by udoluweera on 3/25/17.
 */
public class URLConstants {

    private static final String VERSION_ONE = "v1.0";
    private static final String VERSION_TWO = "v2.0";


    private static final String PUBLIC_API = "/public/api/";
    private static final String SECURE_API = "/secure/api/";


    public static final String CURRENT_PUBLIC_VERSION = PUBLIC_API + VERSION_ONE;
    public static final String CURRENT_SECURE_VERSION = SECURE_API + VERSION_ONE;


    public static final String CODE = "code";
    public static final String NAME = "name";

    public static final String AIRPORTS = "/airports";
    public static final String COUNTRIES = "/countries";
    public static final String INTERNATIONAL = "/international";
    public static final String DOMESTIC = "/domestic";

    public static final String AIRPORT_BY_CODE = AIRPORTS + "/{"+ CODE +"}";

    public static final String AIRPORTS_BY_COUNTRY = AIRPORTS + COUNTRIES + "/{"+ CODE +"}";
    public static final String INTERNATIONAL_AIRPORTS = AIRPORTS + INTERNATIONAL;
    public static final String DOMESTIC_AIRPORTS = AIRPORTS + DOMESTIC;




}
