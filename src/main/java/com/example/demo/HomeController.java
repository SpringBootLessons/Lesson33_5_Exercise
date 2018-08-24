package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class HomeController {
  @Autowired
  EmailService emailService;

  @RequestMapping("/")
  public String GetIndex(){
    return "index";
  }

  @PostMapping("/sendTemplatedEmail")
  public String SendTemplatedEmail(HttpServletRequest request, @RequestParam(
          "file") MultipartFile image) {
    String subject = request.getParameter("subject");
    String name = request.getParameter("name");
    String body = request.getParameter("body");

    try {
      byte[] imageBytes = image.getBytes();
      InputStream inputStream = new ByteArrayInputStream(imageBytes);
      emailService.SendTemplatedEmail(subject, name, body, image, imageBytes);
    }
    catch(IOException e) {
      e.printStackTrace();
    }

    return "success";
  }
}