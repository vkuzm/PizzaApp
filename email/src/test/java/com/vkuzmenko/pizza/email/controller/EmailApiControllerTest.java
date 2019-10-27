package com.vkuzmenko.pizza.email.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkuzmenko.pizza.email.service.ContactEmailService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(EmailApiController.class)
public class EmailApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ContactEmailService contactEmailService;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new EmailApiController(contactEmailService))
                .build();
    }

/*    private final String emailTestApiUrl = "http://localhost/api/email";

    @Test
    public void givenEmail_whenSendContactSuccessfully_thenReturnStatusOk() throws Exception {
        String sendTo = "azeroth999@gmail.com";
        String emailSubject = "Email subject";

        EmailMessageDto email = new EmailMessageDto();
        email.setEmail("test@email.com");
        email.setMessage("Test message");
        //email.setName("User name");
        email.setSubject("Test subject");

        //when(contactEmailService.sendContactForm(email, "1", "5")).;

        mockMvc
                .perform(post(emailTestApiUrl + "/contactForm").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(mapper.writeValueAsBytes(email)))
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));
    }*/
}