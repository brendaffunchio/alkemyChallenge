package com.backend.disney.Services;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService implements IEmailService{

 //@Value("${app.sendgrid.templateId}")
private String templateId= System.getenv("APP_SENDGRID_TEMPLATEID");

@Autowired
 private SendGrid sendGrid;


 @Override
 public String sendEmail(String email){

  try{
Mail mail = prepareMail(email);
Request request= new Request();
request.setMethod(Method.POST);

request.setEndpoint("/sendEmail");
request.setBody(mail.build());

Response response = sendGrid.api(request);

if(response!=null){
 System.out.println("response code from sendgrid"+response.getHeaders());
}

}catch(IOException e){
e.printStackTrace();
return "error in sent email";
  }
  return "Mail has been sent, check your inbox!";
 }

 @Override
 public Mail prepareMail(String email) {
  Mail mail = new Mail();
  Email fromEmail= new Email();

  fromEmail.setEmail("bpdnegocios@gmail.com");

  Email to = new Email();
  to.setEmail(email);

  Personalization personalization = new Personalization();
  personalization.addTo(to);
  mail.setTemplateId(templateId);
  return mail;

 }
}
