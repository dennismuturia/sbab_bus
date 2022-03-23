package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Stops;
import com.dennis.sbab_bus_test.Repositories.StopRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.*;

@Service
public class StopService extends UniversalHttpCall implements JourneyInterface{
    private String model;
    private  final StopRepository stopRepository;


    public StopService(String model, StopRepository stopRepository) throws IOException {
        super(model);
        this.model = model;
        this.stopRepository = stopRepository;

    }
    @Override
    public String httpCall() throws IOException {
        return super.httpCall();
    }
    @Override
    public List<Stops> httpCallData(String httpData) throws IOException {
        List<Stops>busStops = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(httpData);
        JSONObject jo = jsonObject.getJSONObject("ResponseData");
        JSONArray jsonArray = jo.getJSONArray("Result");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject aStop = jsonArray.getJSONObject(i);
            int stoppoint = Integer.parseInt(aStop.getString("StopPointNumber"));
            Stops busStop = new Stops(Integer.parseInt(aStop.getString("StopPointNumber")),
                    aStop.getString("StopPointName"),
                    Float.parseFloat(aStop.getString("LocationNorthingCoordinate")),
                    Float.parseFloat(aStop.getString("LocationEastingCoordinate")));
            busStops.add(busStop);
        }
        return busStops;
    }

    @Override
    public Map<Integer, List<Stops>> filterData(List<Stops> busStops, List<Bus> allBuses) throws MalformedURLException, UnsupportedEncodingException {
        return null;
    }


    @Override
    public Stops getBusStop(int stoppointNumber, List<Stops> busStops) {
        busStops.sort(Comparator.comparingInt(Stops::getStopPointNumber));
        int left = 0;
        int right = busStops.size() -1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            int midNum = busStops.get(mid).getStopPointNumber();
            if(mid < 0 || mid > busStops.size() - 1){

               return new Stops(stoppointNumber);
            }
            else if(midNum == stoppointNumber){
                return busStops.get(mid);
            }else if(midNum>stoppointNumber){
                right = mid -1;
            }else {
                left = mid+1;
            }
        }
        return new Stops(stoppointNumber);
    }


    @Override
    public void saveData(List<Stops> stops) {
        stopRepository.saveAll(stops);
    }

}
