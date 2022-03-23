package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Stops;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@Service
public interface HttpCall {
    List<?> httpCallData(String httpData) throws IOException;
    Map<Integer, List<Stops>> filterData(List<Stops> busStops, List<Bus>allBuses) throws MalformedURLException, UnsupportedEncodingException;
}
