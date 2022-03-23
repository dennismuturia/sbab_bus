package com.dennis.sbab_bus_test.Controllers;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Models;
import com.dennis.sbab_bus_test.Model.Stops;
import com.dennis.sbab_bus_test.Service.FetchBusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trafik")
public class MainController {
    private final FetchBusService fetchBusService;

    public MainController(FetchBusService fetchBusService){
        this.fetchBusService = fetchBusService;
    }
    @GetMapping("/buses")
    public ResponseEntity<List<Bus>>getTopTenBuses(){
        return new ResponseEntity<>(fetchBusService.getTopTenBuses(), HttpStatus.OK);
    }
    @GetMapping("/buses/stops")
    public ResponseEntity<List<Stops>>getStopsPerBus(@RequestBody Bus bus){
        return new ResponseEntity<>(fetchBusService.getStopsOfBus(bus), HttpStatus.OK);
    }
}
