package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Models;
import com.dennis.sbab_bus_test.Model.Stops;
import com.dennis.sbab_bus_test.Repositories.BusRepository;
import com.dennis.sbab_bus_test.Repositories.StopRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.*;

import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;

@Service
public class FetchBusService extends UniversalHttpCall implements BusDataInterface {
    Logger logger = LoggerFactory.getLogger(FetchBusService.class);

    private final BusRepository busRepository;
    private final StopRepository stopRepository;
    private  StopService stopService;


    public FetchBusService(BusRepository busRepository,
                           StopRepository stopRepository,
                           String model) throws IOException {
        super(model);
        this.busRepository = busRepository;
        this.stopRepository = stopRepository;
        this.stopService = new StopService(Models.stop.name(), stopRepository);

    }

    public List<Bus>getTopTenBuses(){
        return busRepository.findAll();
    }

    public List<Stops>getStopsOfBus(Bus bus){
        return stopRepository.findByBus(bus);
    }

    @Override
    public  List<Bus> sortBusWithStops(Map<Integer, List<Stops>>data) {

        List<Bus>total = data.keySet().stream().map(Bus::new).collect(Collectors.toList());
        saveData(total);
        total.forEach(x -> {
            List<Stops>stops = data.get(x.getBusLineNumber());
            stops.forEach(y -> y.setBus(busRepository.findByBusLineNumber(x.getBusLineNumber())));
            stopService.saveData(stops);
        });
        for (Bus b:total) {
            b.setStopList(data.get(b.getBusLineNumber()));
        }
        saveData(total);
        return total;
    }

    public Map<Integer, List<Stops>>sortData(Map<Integer, List<Stops>>data){
        return data.entrySet().stream()
                .sorted(Collections.reverseOrder(comparingInt(e -> e.getValue().size()))).limit(10)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b) -> {throw  new AssertionError();},
                        LinkedHashMap::new));

    }


    @Override
    public void saveData(List<Bus> buses) {

        busRepository.saveAll(buses);
    }
    @Override
    public String httpCall() throws IOException{
       return super.httpCall();
    }

    @Override
    public List<Bus> httpCallData(String httpResponse) throws IOException {
        List<Bus>allBuses = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(httpResponse);
        JSONObject jo = jsonObject.getJSONObject("ResponseData");
        JSONArray jsonArray = jo.getJSONArray("Result");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject aLine = jsonArray.getJSONObject(i);
            Bus newBus = new Bus(Integer.parseInt(aLine.getString("LineNumber")),
                    Integer.parseInt(aLine.getString("JourneyPatternPointNumber")));
            allBuses.add(newBus);

        }
        return allBuses;
    }

    public Map<Integer, List<Stops>> filterData(List<Stops>busStopsList, List<Bus>allBuses) {
        Map<Integer, List<Stops>>busStops = new HashMap<>();
        for (Bus bus: allBuses){
            if(!busStops.containsKey(bus.getBusLineNumber())){
                busStops.put(bus.getBusLineNumber(), new ArrayList<Stops>());
            }
            List<Stops> allStops = busStops.get(bus.getBusLineNumber());
            allStops.add(stopService.getBusStop(bus.getStopPointTrack(), busStopsList));

        }
        return busStops;
    }

}
