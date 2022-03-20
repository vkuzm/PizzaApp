package com.vkuzmenko.pizza.product.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RootApiController.class)
public class RootApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RootApiController rootApiController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(rootApiController)
                .build();
    }

    private final String rootTestApiUrl = "http://localhost/api/";

    @Test
    public void givenLinks_shouldReturnResourceSupportLinks() throws Exception {
        ResourceSupport rootResource = new ResourceSupport();
        rootResource.add(new Link("/api/categories")
                .withRel("categories"));

        rootResource.add(new Link("/api/products")
                .withRel("products"));

        when(rootApiController.root()).thenReturn(rootResource);

        mockMvc
                .perform(get(rootTestApiUrl).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("links[0].rel", is("categories")))
                .andExpect(jsonPath("links[0].href", is("/api/categories")))
                .andExpect(jsonPath("links[1].rel", is("products")))
                .andExpect(jsonPath("links[1].href", is("/api/products")));
    }

    @Test
    public void givenLinks_shouldGenerateResourceSupportLinks() {
        ResourceSupport resourceSupport = new RootApiController().root();
        resourceSupport.add(CategoryApiController.rootUrl());
        resourceSupport.add(ProductApiController.rootUrl());

        assertEquals(resourceSupport.getLinks().get(0).getHref(), CategoryApiController.rootUrl().getHref());
        assertEquals(resourceSupport.getLinks().get(0).getRel(), CategoryApiController.rootUrl().getRel());
        assertEquals(resourceSupport.getLinks().get(1).getHref(), ProductApiController.rootUrl().getHref());
        assertEquals(resourceSupport.getLinks().get(1).getRel(), ProductApiController.rootUrl().getRel());
    }
}