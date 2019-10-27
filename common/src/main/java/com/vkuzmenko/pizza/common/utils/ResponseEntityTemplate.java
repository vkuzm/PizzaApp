package com.vkuzmenko.pizza.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityTemplate {

    private ResponseEntityTemplate() {
    }

    public static <T> ResponseEntity<List<T>> returnList(List<T> list) {
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> returnOne(T element) {
        if (element != null) {
            return new ResponseEntity<>(element, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
