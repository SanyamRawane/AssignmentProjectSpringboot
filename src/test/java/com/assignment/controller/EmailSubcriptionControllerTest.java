package com.assignment.controller;

import com.assignment.service.EmailSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSubcriptionControllerTest {

  @Autowired EmailSubscriptionService emailSubscriptionService;

  EmailSubscriptionController emailSubscriptionController;

  /*
   * @Test void sendEmailToSubscriber() { ResponseEntity<String> expectedResult =
   * new ResponseEntity<String>("Mail Sent Successfully!", HttpStatus.OK);
   * ResponseEntity<String> actualResult =
   * emailSubscriptionController.sendEmailToSubscriber("srawane@galaxe.com",
   * "Test Email Junit", "Do not reply");
   *
   * Assertions.assertTrue(expectedResult.equals(actualResult)); }
   */

}
