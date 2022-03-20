package com.vkuzmenko.pizza.email.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailMessageDto {
    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;
    private String subject;

    @NotEmpty
    private String message;
}
