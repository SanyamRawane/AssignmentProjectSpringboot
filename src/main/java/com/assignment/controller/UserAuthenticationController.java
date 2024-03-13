package com.assignment.controller;

import com.assignment.entity.UserAuthentication;
import com.assignment.service.UserAuthenticationService;
import com.assignment.serviceimpl.UserAuthenticationServiceImpl;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserAuthenticationController {

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	UserAuthenticationServiceImpl serviceImpl;

	private final String errorPrompt = "Error at controller with prompt : ";
	private final Boolean isAdmin = false;

	private Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

	/**
	 * This method allows new user to sign up.
	 *
	 * @param userName,userEmail,userDob,userPassword,isSubscribed :- These are the
	 *                                                             necessary details
	 *                                                             to sign up
	 * @return responseEntity :- It is an object containing http response and
	 *         success prompt.
	 */
	@PostMapping("/registerUser/{userName}/{userEmail}/{userDob}/{userPassword}/{isSubscribed}")
	ResponseEntity<String> registerUser(@PathVariable(name = "userName") String userName,
			@PathVariable(name = "userEmail") String userEmail,
			@PathVariable(name = "userDob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate userDob,
			@PathVariable(name = "userPassword") String userPassword,
			@PathVariable(name = "isSubscribed") Boolean isSubscribed) {
		logger.info("UserAuthenticationController :: registerUser -- begin");
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.registerUser(userName, userEmail,
					userDob, userPassword, isSubscribed, isAdmin), HttpStatus.OK);

		} catch (Exception e) {

			responseEntity = new ResponseEntity<String>(errorPrompt + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: registerUser -- end");
		return responseEntity;
	}

	/**
	 * This method returns the user details object associated with given username of
	 * user willing to login.
	 *
	 * @param userName : user name of user
	 * @return responseEntity : It is an object containing http response and user
	 *         details object.
	 */
	@GetMapping("/loginInformationOfUser/{userName}")
	ResponseEntity<List<UserAuthentication>> loginInformationOfUser(@PathVariable(name = "userName") String userName) {

		logger.info("UserAuthenticationController :: loginInformationOfUser -- begin");
		ResponseEntity<List<UserAuthentication>> responseEntity;

		try {
			responseEntity = new ResponseEntity<List<UserAuthentication>>(
					userAuthenticationService.loginInformationOfUser(userName), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<List<UserAuthentication>>(HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: loginInformationOfUser -- end");
		return responseEntity;
	}

	/**
	 * This method allows user to log out of session
	 *
	 * @param userId - id of user to log out of the session
	 * @return responseEntity - It is an object containing http response and success
	 *         prompt.
	 */
	@PutMapping("/logoutOfSession/{userId}")
	ResponseEntity<String> logOutOfSession(@PathVariable(name = "userId") Long userId) {
		logger.info("UserAuthenticationController :: logOutOfSession -- begin");
		ResponseEntity<String> responseEntity;

		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.logUserOutOfSession(userId),
					HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = new ResponseEntity<String>(
					"Exception At Controller with prompt : " + e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: logOutOfSession -- end");

		return responseEntity;
	}

	/**
	 * This method returns the id of user logged in to the session
	 *
	 * @param none
	 * 
	 * @return responseEntity - It is an object containing http response and id of
	 *         user
	 */
	@GetMapping("/getLoggedInUserId")
	ResponseEntity<Long> getLoggedInUserId() {
		logger.info("UserAuthenticationController :: getLoggedInUserId -- begin");

		ResponseEntity<Long> responseEntity;
		try {
			responseEntity = new ResponseEntity<Long>(userAuthenticationService.getLoggedInUser(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			responseEntity = new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: getLoggedInUserId -- end");
		return responseEntity;
	}

	/**
	 * This method allows user to unsubscribe their account
	 * 
	 * @param userId - It is the user id of user
	 * 
	 * @return responseEntity - It is an object containing Success prompt string
	 *         with http response
	 * 
	 */
	@PutMapping("/unsubscribeUser/{userId}")
	ResponseEntity<String> unSubscibeUser(@PathVariable(name = "userId") Long userId) {
		logger.info("UserAuthenticationController :: unSubscibeUser -- begin");
		ResponseEntity<String> responseEntity;
		try {

			responseEntity = new ResponseEntity<String>(userAuthenticationService.unSubscibeUser(userId),
					HttpStatus.OK);
		} catch (Exception e) {

			responseEntity = new ResponseEntity<String>("Exception At Contriller With Prompt : " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: unSubscibeUser -- end");
		return responseEntity;
	}

	/**
	 * This method logs user into the session
	 * 
	 * @param userName - Its the user name of the user
	 * 
	 * @return responseEntity - It is an object containing Success prompt string
	 *         with http response
	 */
	@PutMapping("/logUserIntoTheSession/{userName}")
	ResponseEntity<String> logUserIntoSession(@PathVariable(name = "userName") String userName) {
		logger.info("UserAuthenticationController :: logUserIntoSession -- begin");

		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.logUserIntoTheSession(userName),
					HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>("Exception At Controller With Prompt : " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: logUserIntoSession -- end");
		return responseEntity;
	}

	/**
	 * This method allows user to retrieve their account.
	 * 
	 * @param userName - Its the user name of the user
	 * 
	 * @return responseEntity - It is an object containing Success prompt string
	 *         with http response
	 */
	@PutMapping("/retrieveAccount/{userName}")
	ResponseEntity<String> retrieveAccount(@PathVariable(name = "userName") String userName) {

		logger.info("UserAuthenticationController :: retrieveAccount -- begin");

		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.retrieveAccount(userName),
					HttpStatus.OK);
		} catch (Exception e) {

			responseEntity = new ResponseEntity<String>("Exception At Controller With Prompt : " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: retrieveAccount -- end");
		return responseEntity;
	}

	/**
	 * This method takes the user name and returns the sign in information of that
	 * user
	 * 
	 * @param userName - Its the user name of the user
	 * 
	 * @return responseEntity - It is an object containing Success prompt string
	 *         with http response
	 */
	@GetMapping("/retrieveInformationOfUser/{userName}")
	ResponseEntity<List<UserAuthentication>> retrieveInformationOfUser(
			@PathVariable(name = "userName") String userName) {

		logger.info("UserAuthenticationController :: loginInformationOfUser -- begin");
		ResponseEntity<List<UserAuthentication>> responseEntity;

		try {
			responseEntity = new ResponseEntity<List<UserAuthentication>>(
					userAuthenticationService.retrieveInformationOfUser(userName), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<List<UserAuthentication>>(HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: loginInformationOfUser -- end");
		return responseEntity;
	}

	/**
	 * This method allows admin user to retrieve the sign in information of users in
	 * the database
	 * 
	 * @param none
	 * 
	 * @return response - It is an object containing List of UserAuthentication
	 *         Objects containing the information of users.
	 * 
	 */
	@GetMapping("/getGeneralAccountsInfo")
	ResponseEntity<List<UserAuthentication>> getGeneralAccountsInfo() {
		logger.info("UserAuthenticationController :: getGeneralAccountsInfo -- begin");

		ResponseEntity<List<UserAuthentication>> responseEntity;
		try {
			responseEntity = new ResponseEntity<List<UserAuthentication>>(
					userAuthenticationService.getGeneralAccountsInfo(), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<List<UserAuthentication>>(HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: getGeneralAccountsInfo -- end");
		return responseEntity;
	}

	/**
	 * This method allows the admin to remove/delete a user account from database
	 * 
	 * @param userId - It is the user id of logged in user
	 * 
	 * @return responseEntity - It is an object containing Success prompt string
	 *         with http response
	 * 
	 */
	@DeleteMapping("/deleteAccountWithUserId/{userId}")
	ResponseEntity<String> deleteAccountWithUserId(@PathVariable(name = "userId") Long userId) {
		logger.info("UserAuthenticationController :: deleteAccountWithUserId -- begin");

		ResponseEntity<String> responseEntity;

		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.deleteAccountWithUserId(userId),
					HttpStatus.OK);
		} catch (Exception e) {

			responseEntity = new ResponseEntity<String>(errorPrompt + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: deleteAccountWithUserId -- end");

		return responseEntity;

	}

	/**
	 * This method verifies the credentials and checks if it is an admin account
	 * 
	 * @param userId - Its the user id of user
	 * 
	 * @return responseEntity - Its an object containing the boolean along with http
	 *         status.
	 * 
	 */
	@GetMapping("/isAdminAccount/{userId}")
	ResponseEntity<Boolean> isAdminAccount(@PathVariable(name = "userId") Long userId) {

		logger.info("UserAuthenticationController :: isAdminAccount -- begin");

		ResponseEntity<Boolean> responseEntity;

		try {
			responseEntity = new ResponseEntity<Boolean>(userAuthenticationService.isAdminAccountById(userId),
					HttpStatus.OK);
		} catch (Exception e) {

			responseEntity = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);

		}
		logger.info("UserAuthenticationController :: isAdminAccount -- end");
		return responseEntity;

	}

	/**
	 * This method allows admin to update username of user
	 * 
	 * @param userId   - Its the user id of user
	 * 
	 * @param userName - Its the updated user name of user
	 * 
	 * @return responseEntity - Its an object containing success prompt and http
	 *         status
	 * 
	 */
	@PutMapping("/updateUserName/{userId}/{userName}")
	ResponseEntity<String> updateUserName(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "userName") String userName) {
		logger.info("UserAuthenticationController :: updateUserName -- begin");
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.updateUserName(userId, userName),
					HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(errorPrompt + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: updateUserName -- end");
		return responseEntity;
	}

	/**
	 * This method allows admin to update email of user
	 * 
	 * @param userId    - Its the user id of user
	 * 
	 * @param userEmail - Its the updated email of user
	 * 
	 * @return responseEntity - Its an object containing success prompt and http
	 *         status
	 * 
	 */
	@PutMapping("/updateUserEmail/{userId}/{userEmail}")
	ResponseEntity<String> updateUserEmail(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "userEmail") String userEmail) {
		logger.info("UserAuthenticationController :: updateUserEmail -- begin");
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.updateUserEmail(userId, userEmail),
					HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(errorPrompt + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: updateUserEmail -- end");
		return responseEntity;
	}

	/**
	 * This method allows admin to update password of user
	 * 
	 * @param userId       - Its the user id of user
	 * 
	 * @param userPassword - Its the updated password of user
	 * 
	 * @return responseEntity - Its an object containing success prompt and http
	 *         status
	 * 
	 */
	@PutMapping("/updateUserPassword/{userId}/{userPassword}")
	ResponseEntity<String> updateUserPassword(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "userPassword") @DateTimeFormat(pattern = "yyyy-MM-dd") String userPassword) {
		logger.info("UserAuthenticationController :: updateUserPassword -- begin");
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new ResponseEntity<String>(
					userAuthenticationService.updateUserPassword(userId, userPassword), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(errorPrompt + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: updateUserPassword -- end");
		return responseEntity;
	}

	/**
	 * This method allows admin to update date of birth of user
	 * 
	 * @param userId   - Its the user id of user
	 * 
	 * @param userName - Its the updated user's date of birth of user
	 * 
	 * @return responseEntity - Its an object containing success prompt and http
	 *         status
	 * 
	 */
	@PutMapping("/updateUserDob/{userId}/{userDob}")
	ResponseEntity<String> updateUserDob(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "userDob") LocalDate userDob) {
		logger.info("UserAuthenticationController :: updateUserDob -- begin");
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new ResponseEntity<String>(userAuthenticationService.updateUserDob(userId, userDob),
					HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(errorPrompt + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("UserAuthenticationController :: updateUserDob -- end");
		return responseEntity;
	}
}
