package com.vkuzmenko.pizza.information.resource;

import com.vkuzmenko.pizza.information.domain.Page;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Date;

@Relation(value = "page", collectionRelation = "pages")
public class PageResource extends ResourceSupport {

    @Getter
    private final long pageId;

    @Getter
    private final String title;

    @Getter
    private final String description;

    @Getter
    private final String shortDescription;

    @Getter
    private final Date createdAt;

    public PageResource(Page page) {
        this.pageId = page.getId();
        this.title = page.getTitle();
        this.description = page.getDescription();
        this.shortDescription = page.getShortDescription();
        this.createdAt = page.getCreatedAt();
    }
}