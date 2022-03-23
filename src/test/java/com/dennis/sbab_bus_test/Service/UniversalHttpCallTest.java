package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Model.Bus;
import com.dennis.sbab_bus_test.Model.Models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UniversalHttpCallTest {
    private UniversalHttpCall universalHttpCall;
    @BeforeEach
    public void init() throws MalformedURLException, UnsupportedEncodingException {
        this.universalHttpCall = new UniversalHttpCall(Models.jour.name());
    }

    @Test
    void httpCall() throws IOException {
        String x = universalHttpCall.httpCall();
      assertEquals(200, universalHttpCall.getStatusCode());
    }
}