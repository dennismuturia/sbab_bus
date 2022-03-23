package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Stops;

import java.util.List;

public interface JourneyInterface extends HttpCall {
    Stops getBusStop(int stoppointNumber, List<Stops> busStops);
    void saveData(List<Stops>stops);
}
