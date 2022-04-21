package com.backend.disney.Services;

import com.sendgrid.Mail;

import java.io.IOException;

public interface IEmailService {

    String sendEmail(String email) throws IOException;
    Mail prepareMail(String email);
}
