package com.vkuzmenko.pizza.admin.helper;

import org.springframework.hateoas.PagedResources;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaginationHelper {
    public static List<Integer> createPagination(PagedResources.PageMetadata metadata) {
        int totalPages = (int) metadata.getTotalPages();
        if (totalPages > 1) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return null;
    }
}
