package com.vkuzmenko.pizza.admin.helper;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ResponsesHelper {
    public static HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static <T> List<T> responseToContent(Collection<Resource<T>> response) {
        return response.stream()
                .map(Resource::getContent)
                .collect(Collectors.toList());
    }
}
