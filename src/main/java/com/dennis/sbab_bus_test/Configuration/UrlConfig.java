package com.dennis.sbab_bus_test.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;


@Configuration
public class UrlConfig {
    @Value("${url}")
    private String urlName;
    @Bean
    public static URL url() throws MalformedURLException {
        return new URL("https://api.sl.se/api2/linedata.json?");
    }
}
