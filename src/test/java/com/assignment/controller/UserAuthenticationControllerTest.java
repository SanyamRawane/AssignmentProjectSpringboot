package com.assignment.controller;

import com.assignment.serviceimpl.UserAuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAuthenticationControllerTest {

  @Autowired UserAuthenticationController userAuthenticationController;

  UserAuthenticationServiceImpl authenticationServiceImpl;

  //	@Test
  //	void registerUser() {
  //		String userName = "Sanyam";
  //		String userEmail = "sanyam.rawane@gmail.com";
  //		String userPassword = "Pass@1234";
  //		LocalDate userDob = LocalDate.of(2001, 12, 04);
  //		Boolean userIsSubscribed = false;
  //
  //		ResponseEntity<String> expectedResult = new ResponseEntity<String>("1 rows Affected",
  // HttpStatus.OK);
  //		ResponseEntity<String> actualResult = userAuthenticationController.registerUser(userName,
  // userEmail, userDob,
  //				userPassword, userIsSubscribed);
  //
  //		Assertions.assertEquals(expectedResult, actualResult);
  //
  //	}

}
