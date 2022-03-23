package com.dennis.sbab_bus_test.Component;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Models;
import com.dennis.sbab_bus_test.Model.Stops;
import com.dennis.sbab_bus_test.Repositories.BusRepository;
import com.dennis.sbab_bus_test.Repositories.StopRepository;
import com.dennis.sbab_bus_test.Service.FetchBusService;
import com.dennis.sbab_bus_test.Service.StopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTasks {
    @Autowired
    private  BusRepository busRepository;
    @Autowired
    private StopRepository stopRepository;
    @Autowired
    private FetchBusService fetchBusService;

    @Autowired
    private StopService stopService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Scheduled(cron = "0 8 * * * *")
    @PostConstruct
    public void scheduledTasks() throws IOException {

        if(busRepository.findAll().size()!=0 || stopRepository.findAll().size() !=0){
            busRepository.truncateBus();
            stopRepository.truncateStops();
        }
        fetchBusService = new FetchBusService(busRepository, stopRepository, Models.jour.toString());
        stopService = new StopService(Models.stop.name(), stopRepository);
        String httpDataBus = fetchBusService.httpCall();
        String httpDataStops = stopService.httpCall();
        List<Bus>allBuses =fetchBusService.httpCallData(httpDataBus);
        List<Stops> busStops = stopService.httpCallData(httpDataStops);
        Map<Integer, List<Stops>> p =fetchBusService.filterData(busStops, allBuses);
        Map<Integer, List<Stops>> sortedMap = fetchBusService.sortData(p);
        fetchBusService.sortBusWithStops(sortedMap);
        logger.info("Running the scheduler");
    }
}
