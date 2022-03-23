package com.dennis.sbab_bus_test.Repositories;

import com.dennis.sbab_bus_test.Model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BusRepository extends JpaRepository<Bus, String> {
    Bus findByBusLineNumber(int lineNumber);

    @Modifying
    @Query(
            value = "truncate table bus",
            nativeQuery = true)
    void truncateBus();
}
