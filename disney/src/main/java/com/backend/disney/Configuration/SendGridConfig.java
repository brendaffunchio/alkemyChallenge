package com.backend.disney.Configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

  //  @Value("${app.sendgrid.key}")
    private String appKey = System.getenv("APP_SENDGRID_KEY");

    @Bean
    public SendGrid getSendGrid(){
      return new SendGrid(appKey);
    }
}
