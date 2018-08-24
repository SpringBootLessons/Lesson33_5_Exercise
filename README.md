# Exercise: Sending an Email Using Gmail with Spring Boot - Templated Email

* Create a Spring Boot project that gets an email subject, name, body 
message, and image as input from the user.
* Using that input, generate a templated email to send to the JavaMailTestMC@gmail.com inbox.
* Attach an inline image to the body of the email as well.
* The email should be formatted as follows:


Dear **[name_in_bold]**,

[Some message inputted by the user]<br />

[inline image]<br />

Yours truly,<br />
javabeans<br />

Hint: Use Cloudinary and MimeMessageHelper.

