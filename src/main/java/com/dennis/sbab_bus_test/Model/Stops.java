package com.dennis.sbab_bus_test.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Data
public class Stops {
    @Id
    private String stopId;
    private int stopPointNumber;
    private String stopPointName;
    private double northCordinate;
    private double eastCordinate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bus_bus_id", nullable = false)
    private Bus bus;

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Stops(){}
    public Stops(int stopPointNumber){
        this.stopId = UUID.randomUUID().toString();
        this.stopPointNumber = stopPointNumber;
    }
    public Stops(int stopPointNumber,
                 String stopPointName,
                 double northCoordinate,
                 double eastCordinate){
        this.stopId = UUID.randomUUID().toString();
        this.stopPointNumber = stopPointNumber;
        this.stopPointName = stopPointName;
        this.northCordinate = northCoordinate;
        this.eastCordinate = eastCordinate;

    }
}
