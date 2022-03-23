package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Models;
import com.dennis.sbab_bus_test.Model.Stops;
import com.dennis.sbab_bus_test.Repositories.BusRepository;
import com.dennis.sbab_bus_test.Repositories.StopRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FetchBusServiceTest {
    @MockBean
    private BusRepository busRepository;
    @MockBean
    private StopRepository stopRepository;
    private FetchBusService fetchBusService;

    List<Bus>buses;
    List<Stops>stopsList;
    String httpData = "{\n" +
            "    \"StatusCode\": 0,\n" +
            "    \"Message\": null,\n" +
            "    \"ExecutionTime\": 682,\n" +
            "    \"ResponseData\": {\n" +
            "        \"Version\": \"2022-03-22 00:13\",\n" +
            "        \"Type\": \"JourneyPatternPointOnLine\",\n" +
            "        \"Result\": [\n" +
            "            {\n" +
            "                \"LineNumber\": \"1\",\n" +
            "                \"DirectionCode\": \"1\",\n" +
            "                \"JourneyPatternPointNumber\": \"10008\",\n" +
            "                \"LastModifiedUtcDateTime\": \"2022-02-15 00:00:00.000\",\n" +
            "                \"ExistsFromDate\": \"2022-02-15 00:00:00.000\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"LineNumber\": \"1\",\n" +
            "                \"DirectionCode\": \"1\",\n" +
            "                \"JourneyPatternPointNumber\": \"10012\",\n" +
            "                \"LastModifiedUtcDateTime\": \"2022-02-16 00:00:00.000\",\n" +
            "                \"ExistsFromDate\": \"2022-02-16 00:00:00.000\"\n" +
            "            },\n" +
            "            ]}}";
    @BeforeEach
    public void init() throws IOException {
        fetchBusService = new FetchBusService(busRepository, stopRepository, Models.jour.name());
        buses = new ArrayList<>(Arrays.asList(new Bus(662, 1), new Bus(663, 4), new Bus(123, 2),
                new Bus(456, 3), new Bus(662, 2), new Bus(662, 3), new Bus(663, 5),
                new Bus(663, 10), new Bus(663, 7), new Bus(663, 2)));
        stopsList = new ArrayList<>(Arrays.asList(new Stops(1, "Stockholm", 89.000, 90.0000),
                (new Stops(2, "TelefonPlan", 89.000, 90.0000)),
                (new Stops(3, "Hornstull", 89.000, 90.0000)),
                (new Stops(4, "T-Centralen", 89.000, 90.0000)),
                (new Stops(5, "Midsommakransen", 89.000, 90.0000)),
                (new Stops(6, "Fruägen", 89.000, 90.0000)),
                (new Stops(7, "Hokmossen", 89.000, 90.0000)),
                (new Stops(10, "Uppsala", 89.000, 90.0000))

        ));
    }

    @Test
    void getTopTenBuses() {
       // busRepository.saveAll(buses);
    }

    @Test
    void httpCallData() throws IOException {
        List<Bus>someBuses = fetchBusService.httpCallData(httpData);
        int[]result = new int[2];
        int i =0;
        for (Bus b: someBuses) {
            result[i] = b.getBusLineNumber();
            i++;
        }
        int[] buses = new int[]{1, 1};
        assertArrayEquals(buses, result);
    }

    @Test
    void filterData() {
        Map<Integer, List<Stops>>result = fetchBusService.filterData(stopsList, buses);
        Map<Integer, List<String>>testRes = new HashMap<>();
        Set<Integer>keySet = result.keySet();
        for (Integer key:keySet) {
            List<String>theres;
            if(!testRes.containsKey(key)){
                theres = new ArrayList<>();
            } else{
                theres = testRes.get(key);
            }
          
            List<Stops>st = result.get(key);

            for (Stops s: st){
                theres.add(s.getStopPointName());
            }
            testRes.put(key, theres);

        }

        assertEquals(Arrays.asList("Stockholm", "TelefonPlan", "Hornstull"), testRes.get(662));
    }

    @Test
    void sortData() {
         Map<Integer, List<Stops>>vals = fetchBusService.filterData(stopsList, buses);
         int[]items = new int[]{663, 662,456,123};
         int[]res = new int[fetchBusService.sortData(vals).size()];
         int count = 0;
         for (int x:fetchBusService.sortData(vals).keySet()) {
           res[count++] = x;
         }
         assertArrayEquals(items, res);

    }
}