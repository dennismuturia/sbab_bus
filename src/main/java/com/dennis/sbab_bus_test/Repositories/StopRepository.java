package com.dennis.sbab_bus_test.Repositories;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Stops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StopRepository extends JpaRepository<Stops, String> {
    List<Stops> findByBus(Bus bus);
    @Modifying
    @Query(
            value = "truncate table stops",
            nativeQuery = true)
    void truncateStops();
}
