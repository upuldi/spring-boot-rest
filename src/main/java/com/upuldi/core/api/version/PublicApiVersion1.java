package com.upuldi.core.api.version;

import com.upuldi.core.util.URLConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is designed to be used with public API version one endpoints.
 * Controllers with this annotation will serve their services in public area of the app
 *
 * Created by udoluweera on 3/25/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping(value = URLConstants.CURRENT_PUBLIC_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public @interface PublicApiVersion1 {}

