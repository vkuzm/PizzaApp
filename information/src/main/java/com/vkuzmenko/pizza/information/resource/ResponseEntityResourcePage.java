package com.vkuzmenko.pizza.information.resource;

import com.vkuzmenko.pizza.information.domain.Page;
import org.springframework.hateoas.Resources;

import java.util.List;

public class ResponseEntityResourcePage {

    private ResponseEntityResourcePage() {
    }

    public static Resources<PageResource> convert(List<Page> pages) {
        List<PageResource> pageResources
                = new PageResourceAssembler().toResources(pages);

        return new Resources<>(pageResources);
    }

    public static PageResource convert(Page page) {
        return new PageResourceAssembler().toResource(page);
    }
}
