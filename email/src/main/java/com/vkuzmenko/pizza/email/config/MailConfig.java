package com.vkuzmenko.pizza.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail")
@Data
public class MailConfig {
  private String host;
  private int port;
  private String login;
  private String password;
  private String debug;
}