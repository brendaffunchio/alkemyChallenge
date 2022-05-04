package com.backend.disney.Services.impl;

import com.backend.disney.Services.IEmailService;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService implements IEmailService {

    @Value("${app.sendgrid.templateId}")
    private String templateId; /*=System.getenv("APP_SENDGRID_TEMPLATEID")*/

    @Autowired
    private SendGrid sendGrid;


    @Override
    public void sendEmail(String email) throws IOException {
        try {
            Mail mail = prepareMail(email);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("auth/register");

            request.setBody(mail.build());

            sendGrid.api(request);
        }   catch (IOException e) {
        e.printStackTrace();
        throw new IOException();
        }

       /* try {
            Mail mail = prepareMail(email);
            Request request = new Request();
            request.setMethod(Method.POST);

            request.setEndpoint("/auth/register");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            if (response != null) {
                System.out.println("response code from sendgrid" + response.getHeaders());
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }*/

    }

    @Override
    public Mail prepareMail(String email) {

        Mail mail = new Mail();
        Email fromEmail = new Email();

        fromEmail.setEmail("brendaffunchio@gmail.com");

        Email to = new Email();
        to.setEmail(email);

        Personalization personalization = new Personalization();
        personalization.addTo(to);
        mail.setTemplateId(templateId);

        return mail;

    }
}
