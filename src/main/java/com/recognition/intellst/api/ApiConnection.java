package com.recognition.intellst.api;

import org.springframework.web.client.RestTemplate;

public class ApiConnection {

    private static void sentRequest() {
        final String uri = "http://intellst-back.local/api/identified-case";

        RestTemplate restTemplate = new RestTemplate();

        DataToSent dataToSent = restTemplate.getForObject(uri, DataToSent.class);
    }
}
