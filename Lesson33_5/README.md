# Sending Email using GMail with Spring Boot - Templated Email

## Learning Objectives
* Create a reusable email template
* Send an email with content inputted by the user

## The Walkthrough

1. Start with the code from the previous lesson

2. Create a mail template:
  * Right click on templates and click New -> Html
  * Name it mailtemplate.html
  * Edit it to look like this:

```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head></head>
<body>
    <p>Hello,</p>

    <p><span th:text="${message}"></span></p>

    <p>
        Sincerely,<br/>
        <strong>javabeans</strong>
    </p>
</body>
</html>
```


3. Edit the index template
  * Revise it to look like this:

```html
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form action="/sendTemplatedEmail" method="post">
        <button type="submit">Send templated email</button>
    </form>
</body>
</html>
```

4. Edit the EmailService.java class
  * Add the following method:
  
```java
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
```


5. Edit the HomeController
  * Add the following method:

```Java
	@PostMapping("/sendTemplatedEmail")
	public String SendTemplatedEmail() {
		emailService.SendTemplatedEmail("A templated email message.");
		return "success";
	}
```

6. Run your application and open a browser, and you should see this:
![](https://github.com/romielisse/springlessonimg/blob/master/templatedemail.png)

If you click on the button, you should see this:
![](https://github.com/romielisse/springlessonimg/blob/master/emailsent.png)

If you log into the gmail account provided, you should be able to locate the email that was just sent.

## Congratulations!

You've successfully sent an email using a Thymeleaf template. A templated email allows you to send emails with images and attachments rather than just plain text.

**Source**<br />
https://tinyurl.com/yar6guoq
