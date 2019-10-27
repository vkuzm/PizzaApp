package com.vkuzmenko.pizza.information.resource;

import com.vkuzmenko.pizza.information.controller.PageApiController;
import com.vkuzmenko.pizza.information.domain.Page;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class PageResourceAssembler extends ResourceAssemblerSupport<Page, PageResource> {

    public PageResourceAssembler() {
        super(PageApiController.class, PageResource.class);
    }

    @Override
    protected PageResource instantiateResource(Page page) {
        return new PageResource(page);
    }

    @Override
    public PageResource toResource(Page page) {
        return createResourceWithId(page.getId(), page);
    }
}
