package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Models;
import com.dennis.sbab_bus_test.Model.Stops;
import com.dennis.sbab_bus_test.Repositories.StopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StopServiceTest {
    @MockBean
    private StopRepository stopRepository;
    @Autowired
    private StopService stopService;
    List<Stops>stopsList;
    String httpJson = "{\n" +
            "    \"StatusCode\": 0,\n" +
            "    \"Message\": null,\n" +
            "    \"ExecutionTime\": 405,\n" +
            "    \"ResponseData\": {\n" +
            "        \"Version\": \"2022-03-21 00:13\",\n" +
            "        \"Type\": \"StopPoint\",\n" +
            "        \"Result\": [\n" +
            "            {\n" +
            "                \"StopPointNumber\": \"10001\",\n" +
            "                \"StopPointName\": \"Stadshagsplan\",\n" +
            "                \"StopAreaNumber\": \"10001\",\n" +
            "                \"LocationNorthingCoordinate\": \"59.3373922532088\",\n" +
            "                \"LocationEastingCoordinate\": \"18.0215057110782\",\n" +
            "                \"ZoneShortName\": \"A\",\n" +
            "                \"StopAreaTypeCode\": \"BUSTERM\",\n" +
            "                \"LastModifiedUtcDateTime\": \"2022-02-26 00:00:00.000\",\n" +
            "                \"ExistsFromDate\": \"2022-02-26 00:00:00.000\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"StopPointNumber\": \"10002\",\n" +
            "                \"StopPointName\": \"John Bergs plan\",\n" +
            "                \"StopAreaNumber\": \"10002\",\n" +
            "                \"LocationNorthingCoordinate\": \"59.3361450073188\",\n" +
            "                \"LocationEastingCoordinate\": \"18.0222866342593\",\n" +
            "                \"ZoneShortName\": \"A\",\n" +
            "                \"StopAreaTypeCode\": \"BUSTERM\",\n" +
            "                \"LastModifiedUtcDateTime\": \"2015-09-24 00:00:00.000\",\n" +
            "                \"ExistsFromDate\": \"2015-09-24 00:00:00.000\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"StopPointNumber\": \"10003\",\n" +
            "                \"StopPointName\": \"John Bergs plan\",\n" +
            "                \"StopAreaNumber\": \"10002\",\n" +
            "                \"LocationNorthingCoordinate\": \"59.3362549983713\",\n" +
            "                \"LocationEastingCoordinate\": \"18.0220232520707\",\n" +
            "                \"ZoneShortName\": \"A\",\n" +
            "                \"StopAreaTypeCode\": \"BUSTERM\",\n" +
            "                \"LastModifiedUtcDateTime\": \"2015-09-24 00:00:00.000\",\n" +
            "                \"ExistsFromDate\": \"2015-09-24 00:00:00.000\"\n" +
            "            }]}}";

    @BeforeEach
    public void init() throws IOException {
        stopsList = new ArrayList<>(Arrays.asList(new Stops(1, "Stockholm", 89.000, 90.0000),
                (new Stops(2, "TelefonPlan", 89.000, 90.0000)),
                (new Stops(3, "Hornstull", 89.000, 90.0000)),
                (new Stops(4, "T-Centralen", 89.000, 90.0000)),
                (new Stops(5, "Midsommakransen", 89.000, 90.0000)),
                (new Stops(6, "Fruägen", 89.000, 90.0000)),
                (new Stops(7, "Hokmossen", 89.000, 90.0000)),
                (new Stops(10, "Uppsala", 89.000, 90.0000))));
        stopService = new StopService(Models.stop.name(), stopRepository);
    }

    @Test
    void httpCallData() throws IOException {
        String[]stopArray = new String[]{"Stadshagsplan", "John Bergs plan", "John Bergs plan"};
        List<Stops> stopsFromJSON=stopService.httpCallData(httpJson);
        String[]stops = new String[3];
        for (int i = 0; i < stopsFromJSON.size(); i++) {
            stops[i] = stopsFromJSON.get(i).getStopPointName();
        }
        assertArrayEquals(stopArray, stops);
    }

    @Test
    void filterData() {


    }

    @Test
    void getBusStop() {
        assertEquals("Midsommakransen", stopService.getBusStop(5, stopsList).getStopPointName());
    }
}