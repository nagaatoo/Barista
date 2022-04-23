package ru.numbDev.barista.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestUtils {

    private final static RestTemplate restTemplate = new RestTemplate();

    public static <T> T doGet(String url, Class<T> clazz) {
        return restTemplate.getForObject(url, clazz);
    }

    public static <T> T doPost(String url, Class<T> clazz, Map<String, Object> body, HttpHeaders headers) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<T> response = restTemplate.postForEntity(url, entity, clazz);

        if (response.getStatusCode() != HttpStatus.OK || response.getStatusCode() != HttpStatus.CREATED) {
            String message = "Request had been declined to url: " + url;
            throw ThrowUtils.apiEx(message, 500);
        }

        return response.getBody();
    }
}
