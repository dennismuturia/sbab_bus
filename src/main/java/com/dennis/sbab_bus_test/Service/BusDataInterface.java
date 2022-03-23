package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Stops;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BusDataInterface {
    List<Bus>sortBusWithStops(Map<Integer, List<Stops>> data);
        void saveData(List<Bus>buses);
    List<Bus> httpCallData(String httpResponse) throws IOException;
}
