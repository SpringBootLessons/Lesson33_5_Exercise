# Sending Email using GMail with Spring Boot - Simple Email

## Learning Objectives
* Configuring email service provider credentials
* Create an SMTP connection
* Sending out a simple email

## The Walkthrough

1. Create a Spring Boot Application
  * Name it SpringEmail
  * Add the dependencies for web and thymeleaf
  * Hit next until you finish the wizard, and then wait until it's done.


2. Add Java Mail dependency
  * Open up your pom.xml
  * Add the following dependency:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

3. Re-import your maven dependencies:
  * Either click the Import Changes link in the lower right hand corner, or
  * Right-click your project and click Maven -> Re-Import
  * Wait for the background tasks to complete


4. Create the index template
  * Right click on templates and click New -> Html
  * Name it index.html
  * Edit it to look like this:

```html
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
  <form action="/sendSimpleEmail" method="post">
      <button type="submit">Send simple email</button>
  </form>
</body>
</html>
```

5. Create the redirect template after the email is sent
  * Right click on templates and click New -> Html
  * Name it success.html
  * Edit it to look like this:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>Email sent!</h1>
</body>
</html>
```

6. Create an Email Service class
  * Right click on com.example.demo -> New -> Java Class
  * Name it EmailService.java
  * Edit it to look like this:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.*;
import javax.mail.*;
import javax.mail.internet.*;
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

    public void SendSimpleEmail(){
        try {

            Message message = new MimeMessage(GetSession());

            // The email address you're sending from
            message.setFrom(new InternetAddress("JavaMailTestMC@gmail.com"));

            // The email address(es) you're sending the email to
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("JavaMailTestMC@gmail.com"));

            // Email subject
            message.setSubject("Hello World");

            // Email content
            message.setText("Hello Earth!");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
```

7. Create the HomeController
  * Right click on com.example.demo -> New -> Java Class
  * Name it HomeController.java
  * Edit it to look like this:

```Java
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
  @Autowired
  EmailService emailService;

  @RequestMapping("/")
  public String GetIndex(){
      return "index";
  }

  @PostMapping("/sendSimpleEmail")
  public String SendSimpleEmail() {
      emailService.SendSimpleEmail();
      return "success";
  }
}
```
8. Run your application and open a browser, and you should see this:
![](https://github.com/romielisse/springlessonimg/blob/master/simpleemail.png)

If you click on the button, you should see this:
![](https://github.com/romielisse/springlessonimg/blob/master/emailsent.png)

If you log into the gmail account provided, you should be able to locate the email that was just sent.

## What's Going On?

JavaMail is an API that is used to compose, write and read electronic messages (emails).

There are some protocols that are used in JavaMail API.
* SMTP
* POP
* IMAP
* MIME
* etc.

SMTP is an acronym for Simple Mail Transfer Protocol. It provides a mechanism to deliver the email. We can use Apache James server, Postcast server, cmail server etc. as an SMTP server. But if we purchase the host space, an SMTP server is b ydefault provided by the host provider. SMTP usually uses TCP port 25.

### JavaMail API Core Classes ###
There are two packages that are used in Java Mail API: javax.mail and javax.mail.internet package. These packages contains many classes for Java Mail API. They are:
* javax.mail.Session class
* javax.mail.Message class
* javax.mail.internet.MimeMessage class
* javax.mail.internet.InternetAddress class
* javax.mail.PasswordAuthentication class
* javax.mail.Transport class
* etc.

**mail.smtp.starttls.enable**
This encrypts the SMTP connection.

**mail.smtp.auth**
If true, attempt to authenticate the user using the AUTH command. Defaults to false.

**mail.smtp.host**
The SMTP server to connect to.

**mail.smtp.port**
The SMTP server port to connect to, if the connect() method doesn't explicitly specify one. Defaults to 25.

**Source**<br />
https://tinyurl.com/yas59lx2<br />
https://tinyurl.com/yaxoz2ba
