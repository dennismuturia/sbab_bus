package com.dennis.sbab_bus_test.Service;

import com.dennis.sbab_bus_test.Configuration.ParameterStringBuilder;
import com.dennis.sbab_bus_test.Configuration.UrlConfig;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public  class UniversalHttpCall{

    private final URL url;
    private final Map<String, String>params;
    private int code;
    protected UniversalHttpCall(String model) throws MalformedURLException, UnsupportedEncodingException {
        params = buildPrams(model);
        this.url = new URL(UrlConfig.url() + ParameterStringBuilder.getParamsString(params));
    }

    private Map<String, String> buildPrams(String model){
        Map<String, String>paramMap = new HashMap<>();
        paramMap.put("key", "79af0cbbdff04beda7ea774ecaa51343");
        paramMap.put("model", model);
        paramMap.put("DefaultTransportModeCode", "BUS");
        return paramMap;
    }

    public String httpCall() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        this.code = connection.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return content.toString();
    }

    public int getStatusCode(){
        return code;
    }
}
