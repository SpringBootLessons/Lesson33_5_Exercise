package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

@Service
public class EmailService {

  private TemplateEngine templateEngine;

  @Autowired
  Environment env;

  @Autowired
  CloudinaryConfig cloudc;

  @Autowired
  public EmailService(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  private Properties GetProperties(){
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
    props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
    props.put("mail.smtp.host", env.getProperty("mail.smtp.host"));
    props.put("mail.smtp.port", env.getProperty("mail.smtp.port"));

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

  public String BuildTemplateWithContent(String message, String name,
                                         MultipartFile image) {
    String imageURL = "";
    Context context = new Context();
    context.setVariable("name", name);
    context.setVariable("message", message);

    try {
      Map uploadResult = cloudc.upload(image.getBytes(),
              ObjectUtils.asMap("resourcetype", "auto"));
      imageURL = uploadResult.get("url").toString();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    context.setVariable("imageID", imageURL);
    return templateEngine.process("mailtemplate", context);
  }

  public void SendTemplatedEmail(String subject, String name, String message,
                                 MultipartFile image, byte[] imageBytes){
    try {
      MimeMessage mimeMessage = new MimeMessage(GetSession());
      final MimeMessageHelper mimeMessageHelper
              = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      String templateContent = BuildTemplateWithContent(message, name,
              image);

      // The email address you're sending from
      mimeMessageHelper.setFrom(new InternetAddress("JavaMailTestMC@gmail.com"));

      // The email address(es) you're sending the email to
      mimeMessageHelper.setTo(InternetAddress.parse("JavaMailTestMC@gmail" +
              ".com"));

      // Email subject
      mimeMessageHelper.setSubject(subject);

      //===============Set email content===============

      // Add image
      final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
      mimeMessageHelper.addInline(image.getName(), imageSource,
              image.getContentType());

      // Add template
      mimeMessageHelper.setText(templateContent, true);

      // Send email
      Transport.send(mimeMessage);

    } catch (Exception e) {
        e.printStackTrace();
    }
  }

}