package com.assignment.controller;

import com.assignment.dto.EmailRequest;
import com.assignment.dto.EmailResponse;
import com.assignment.service.EmailSubscriptionService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class EmailSubscriptionController {

  @Autowired private EmailSubscriptionService emailSubscriptionService;

  private Logger logger = LoggerFactory.getLogger(EmailSubscriptionController.class);

  private final String errorPrompt = "Error Ar Controller With Prompt : ";

  /**
   * This method sends the designed automated email to given email id with given subject
   *
   * @param emailTo,emailSubject,emailBody :- Email details to locate user
   * @return responseEntity :- It is an object containing http response and success prompt.
   */
  @PostMapping("/sendEmailToSubscriber/{emailTo}/{emailSubject}/{emailBody}")
  ResponseEntity<String> sendEmailToSubscriber(
      @PathVariable(name = "emailTo") String emailTo,
      @PathVariable(name = "emailSubject") String emailSubject,
      @PathVariable(name = "emailBody") String emailBody) {

    logger.info("EmailSubscriptionController :: sendEmailToSubscriber -- begin");
    ResponseEntity<String> responseEntity;

    try {
      responseEntity =
          new ResponseEntity<String>(
              emailSubscriptionService.sendEmailToSubscriber(emailTo, emailSubject, emailBody),
              HttpStatus.OK);
    } catch (Exception e) {
      responseEntity = new ResponseEntity<String>(errorPrompt + e.getMessage(), HttpStatus.OK);
    }
    logger.info("EmailSubscriptionController :: sendEmailToSubscriber -- end");
    return responseEntity;
  }

  /**
   * This method sends the designed Email making the use of templates i.e. formatted email
   *
   * @param emailRequest - Its an object containing email metadata
   * @return responseEntity - It is an object containing http response and an object EmailResponse
   *     indication the success of that email.
   */
  @PostMapping("/sendEmailAsTemplate")
  ResponseEntity<EmailResponse> sendEmailAsTemplate(@RequestBody EmailRequest emailRequest) {

    logger.info("EmailSubscriptionController :: sendEmailAsTemplate -- begin");

    ResponseEntity<EmailResponse> responseEntity;
    EmailResponse emailResponse = new EmailResponse("Error At Controller", false);
    Map<String, Object> map = new HashMap<>();
    map.put("Name", emailRequest.getNameOfEmailOwner());
    map.put("location", "Banglore,India");
    try {
      responseEntity =
          new ResponseEntity<>(
              emailSubscriptionService.sendEmailAsTemplate(emailRequest, map), HttpStatus.OK);

    } catch (Exception e) {
      responseEntity = new ResponseEntity<EmailResponse>(emailResponse, HttpStatus.BAD_REQUEST);
    }
    logger.info("EmailSubscriptionController :: sendEmailAsTemplate -- end");
    return responseEntity;
  }
}
