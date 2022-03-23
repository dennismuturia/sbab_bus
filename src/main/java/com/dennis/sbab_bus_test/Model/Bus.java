package com.dennis.sbab_bus_test.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Data
@Table(name = "bus")
public class Bus{
    @Id
    private String busId;
    private int busLineNumber;
    private int direction;
    private int stopPointTrack;
    @OneToMany(mappedBy = "bus")
    private List<Stops>stopList;

    public Bus(){}
    public Bus(int busLineNumber, List<Stops>stopList){
        this.busId = UUID.randomUUID().toString()+ String.valueOf(new Random().nextLong());
        this.busLineNumber = busLineNumber;
        this.stopList = stopList;
    }

    public Bus(int busLineNumber, int stopPointTrack){
        this.busLineNumber = busLineNumber;
        this.stopPointTrack = stopPointTrack;
    }
    public Bus(int busLineNumber){
        this.busId = UUID.randomUUID().toString()+ String.valueOf(new Random().nextLong());
        this.busLineNumber = busLineNumber;
    }

}
