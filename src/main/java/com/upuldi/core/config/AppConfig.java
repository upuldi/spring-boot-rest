package com.upuldi.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Main Config class, Different type of configuration can be defined in different classes.
 * Like Persistence,Web etc...
 *
 * Created by udoluweera on 3/26/17.
 */
@Configuration
public class AppConfig {

    @Bean
    public CacheManager getEhCacheManager(){
        return  new EhCacheCacheManager(getEhCacheFactory().getObject());
    }
    @Bean
    public EhCacheManagerFactoryBean getEhCacheFactory(){
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factoryBean.setShared(true);
        return factoryBean;
    }

}
