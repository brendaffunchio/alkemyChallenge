package com.backend.disney.Services.impl;

import com.backend.disney.Repositories.IUsuarioRepository;
import com.backend.disney.Services.IEmailService;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService implements IEmailService {

    @Value("${app.sendgrid.templateId}")
    private String templateId;


    @Autowired
    private Configuration config;
    @Autowired
    private SendGrid sendGrid;

    @Autowired
    private IUsuarioRepository repository;

    @Override
    public void sendEmail(String email) throws IOException {
        try {
            Mail mail = prepareMail(email);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            request.setBody(mail.build());

            Response response = sendGrid.api(request);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new IOException("Cannot send email");
        }
    }




    @Override
    public Mail prepareMail(String email) throws TemplateException, IOException {

        Mail mail = new Mail();
        Email fromEmail = new Email();
        Content content = new Content();

        content.setType("text/html");
        content.setValue(prepareWelcomeTemplate(email));

        fromEmail.setEmail("disneyapp@yopmail.com");
        mail.setFrom(fromEmail);
        Email to = new Email();
        to.setEmail(email);

        Personalization personalization = new Personalization();
        personalization.addTo(to);

        mail.addContent(content);
        mail.setSubject("Disney App Registration");
        // mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);

        return mail;

    }

    @Override
    public String prepareWelcomeTemplate(String email) throws IOException, TemplateException {

        Map<String, Object> model = new HashMap<>();

        model.put("email", email);

        return FreeMarkerTemplateUtils.processTemplateIntoString(config.getTemplate("templateEmail.html"), model);
    }
}
