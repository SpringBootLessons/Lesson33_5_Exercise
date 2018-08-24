package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

  private TemplateEngine templateEngine;

  @Autowired
  public EmailService(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  private Properties GetProperties(){
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    return  props;
  }

  private Session GetSession(){
    // Email account credentials
    final String username = "JavaMailTestMC@gmail.com";
    final String password = "helloworldpasswordtest";

    // Create session with username and password
    Session session = Session.getInstance(GetProperties(),
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
              }
            });

    return  session;
  }

  public String BuildTemplateWithContent(String message) {
    Context context = new Context();
    context.setVariable("message", message);
    return templateEngine.process("mailtemplate", context);
  }

  public void SendTemplatedEmail(String message){
    try {
      Message mimeMessage = new MimeMessage(GetSession());
      String content = BuildTemplateWithContent(message);

      // The email address you're sending from
      mimeMessage.setFrom(new InternetAddress("JavaMailTestMC@gmail.com"));

      // The email address(es) you're sending the email to
      mimeMessage.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse("JavaMailTestMC@gmail.com"));

      // Email subject
      mimeMessage.setSubject("Templated email test");

      // Set email content with template
      mimeMessage.setContent(content, "text/html; charset=utf-8");

      // Send email
      Transport.send(mimeMessage);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

}