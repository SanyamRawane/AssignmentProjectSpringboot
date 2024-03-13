package com.assignment.serviceimpl;

import com.assignment.entity.UserAuthentication;
import com.assignment.repository.UserAuthenticationRepository;
import com.assignment.service.UserAuthenticationService;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

	private Logger logger = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

	private final String errorPrompt = "Error Occured At ServiceImpl With Prompt : ";

	@Autowired
	private UserAuthenticationRepository userAuthenticationRepository;

	/**
	 * This method signs up user with given details.
	 *
	 * @param userName,userEmail,userDob,userPassword,isSubscribed :- These are the
	 *                                                             necessary details
	 *                                                             to sign up
	 * @return successPrompt :- success prompt of the operation
	 */
	@Override
	public String registerUser(String userName, String userEmail, LocalDate userDob, String userPassword,
			Boolean isSubscribed, Boolean isAdminAccount) throws Exception {
		logger.info("UserAuthenticationServiceImpl :: registerUser -- begin");
		Boolean isloggedIn = true;
		Integer successCode = -1;
		String successPrompt;
		List<UserAuthentication> userAuthentications;

		userAuthentications = userAuthenticationRepository.loginInformationOfUser(userName);
		logger.info("The list is as follows" + userAuthentications.size());
		if (userAuthentications.size() != 0) {
			throw new Exception("UserName Already Exists");
		} else {
			successCode = userAuthenticationRepository.registerUser(userName, userEmail, userDob, userPassword,
					isSubscribed, isloggedIn, isAdminAccount);
			successPrompt = successCode == 0 ? "No rows affected" : successCode + " rows Affected";
		}

		logger.info("UserAuthenticationServiceImpl :: registerUser -- end");
		return successPrompt;
	}

	/**
	 * This method returns list of user details object with matching username
	 *
	 * @param userName :- It is the username of user
	 * @return userAuthentications :- list of user details with matching user name
	 */
	@Override
	public List<UserAuthentication> loginInformationOfUser(String userName) {

		logger.info("UserAuthenticationServiceImpl :: loginInformationOfUser -- begin");
		List<UserAuthentication> userAuthentications;
		try {
			userAuthentications = userAuthenticationRepository.loginInformationOfUser(userName);

		} catch (Exception e) {
			userAuthentications = null;
		}

		logger.info("UserAuthenticationServiceImpl :: loginInformationOfUser -- end");
		return userAuthentications;
	}

	@Override
	public String logUserOutOfSession(Long loggedInUserId) {

		String successPrompt;
		Integer successCode;
		try {
			successCode = userAuthenticationRepository.logOutSession(loggedInUserId);
			successPrompt = successCode == 0 ? "Could Not Log Out" : "Logged Out Successfully";

		} catch (Exception e) {
			successPrompt = errorPrompt + e.getLocalizedMessage();
		}

		return successPrompt;
	}

	@Override
	public Long getLoggedInUser() {
		logger.info("UserAuthenticationServiceImpl :: getLoggedInUser -- begin");

		Long loggedInUser = 0l;
		try {
			loggedInUser = userAuthenticationRepository.getLoggedInSession();

		} catch (Exception e) {
			throw e;
		}
		logger.info("UserAuthenticationServiceImpl :: getLoggedInUser -- end");
		return loggedInUser;
	}

	@Override
	public String unSubscibeUser(Long loggedInSesionId) {

		logger.info("UserAuthenticationServiceImpl :: unSubscibeUser -- begin");

		Integer successCode;
		String successPrompt;
		try {
			successCode = userAuthenticationRepository.unsubscribeUser(loggedInSesionId);
			logger.info("UserAuthenticationServiceImpl :: unSubscibeUser : try -> successcode - " + successCode);
			successPrompt = successCode != 0 ? "Success" : "Failure";
			logger.info("UserAuthenticationServiceImpl :: unSubscibeUser : try -> successPrompt - " + successPrompt);
		} catch (Exception e) {
			successPrompt = errorPrompt + e.getMessage();
		}
		logger.info("UserAuthenticationServiceImpl :: unSubscibeUser -- end");
		return successPrompt;
	}

	@Override
	public String logUserIntoTheSession(String userName) {

		Integer successCode;
		String successPrompt;
		try {
			successCode = userAuthenticationRepository.logUserInSession(userName);
			successPrompt = successCode == 0 ? "Failed To LogIn" : "Logged In Sucess";
		} catch (Exception e) {
			successPrompt = errorPrompt + e.getMessage();
		}

		return successPrompt;
	}

	@Override
	public String retrieveAccount(String userName) {
		Integer successCode;
		String successPrompt;
		try {
			successCode = userAuthenticationRepository.retrieveAccount(userName);
			successPrompt = successCode == 0 ? "Failed To Retrieve Account" : "Sucessfully Retrieved Account";
		} catch (Exception e) {
			successPrompt = errorPrompt + e.getMessage();
		}

		return successPrompt;
	}

	@Override
	public List<UserAuthentication> retrieveInformationOfUser(String userName) {
		logger.info("UserAuthenticationServiceImpl :: retrieveInformationOfUser -- begin");
		List<UserAuthentication> userAuthentications;
		try {
			userAuthentications = userAuthenticationRepository.retrieveInformationOfUser(userName);

		} catch (Exception e) {
			userAuthentications = null;
		}

		logger.info("UserAuthenticationServiceImpl :: retrieveInformationOfUser -- end");
		return userAuthentications;
	}

	@Override
	public List<UserAuthentication> getGeneralAccountsInfo() {
		logger.info("UserAuthenticationServiceImpl :: getGeneralAccountsInfo -- begin");

		List<UserAuthentication> userAuthentications;
		try {
			userAuthentications = userAuthenticationRepository.getGeneralAccountsInfo();

		} catch (Exception e) {
			userAuthentications = null;
			throw e;
		}
		logger.info("UserAuthenticationServiceImpl :: getGeneralAccountsInfo -- end");

		return userAuthentications;
	}

	@Override
	public String deleteAccountWithUserId(Long userId) {

		logger.info("UserAuthenticationServiceImpl :: deleteAccountWithUserId -- begin");

		String successPrompt;

		Integer successCode;

		try {
			successCode = userAuthenticationRepository.deleteAccountWithUserId(userId);
			successPrompt = successCode == 0 ? "No Account Deleted" : "Account Deleted Successfully";

		} catch (Exception e) {

			successPrompt = errorPrompt + e.getMessage();
		}

		logger.info("UserAuthenticationServiceImpl :: deleteAccountWithUserId -- end");

		return successPrompt;
	}

	@Override
	public Boolean isAdminAccountById(Long userId) {

		logger.info("UserAuthenticationServiceImpl :: isAdminAccountById -- begin");

		Boolean isAdmin;

		try {
			isAdmin = userAuthenticationRepository.isAdminAccountById(userId);
		} catch (Exception e) {

			isAdmin = null;
			logger.error("UserAuthenticationServiceImpl :: isAdminAccountById -- Exception : " + e.getMessage());

		}
		logger.info("UserAuthenticationServiceImpl :: isAdminAccountById -- end");
		return isAdmin;
	}

	@Override
	public String updateUserName(Long userId, String userName) {
		logger.info("UserAuthenticationServiceImpl :: updateUserName -- begin");
		String successPrompt;
		Integer successCode = 0;
		try {
			successCode = userAuthenticationRepository.updateUserName(userId, userName);
			successPrompt = successCode == 0 ? "No rows updated" : "Successfully Updated";
		} catch (Exception e) {
			successPrompt = errorPrompt + e.getMessage();

		}
		logger.info("UserAuthenticationServiceImpl :: updateUserName -- end");
		return successPrompt;
	}

	@Override
	public String updateUserEmail(Long userId, String userEmail) {
		logger.info("UserAuthenticationServiceImpl :: updateUserEmail -- begin");
		String successPrompt;
		Integer successCode = 0;
		try {
			successCode = userAuthenticationRepository.updateUserEmail(userId, userEmail);
			successPrompt = successCode == 0 ? "No rows updated" : "Successfully Updated";
		} catch (Exception e) {
			successPrompt = errorPrompt + e.getMessage();

		}
		logger.info("UserAuthenticationServiceImpl :: updateUserEmail -- end");
		return successPrompt;
	}

	@Override
	public String updateUserPassword(Long userId, String userPassword) {
		logger.info("UserAuthenticationServiceImpl :: updateUserPassword -- begin");
		String successPrompt;
		Integer successCode = 0;
		try {
			successCode = userAuthenticationRepository.updateUserPassword(userId, userPassword);
			successPrompt = successCode == 0 ? "No rows updated" : "Successfully Updated";
		} catch (Exception e) {
			successPrompt = errorPrompt + e.getMessage();

		}
		logger.info("UserAuthenticationServiceImpl :: updateUserPassword -- end");
		return successPrompt;
	}

	@Override
	public String updateUserDob(Long userId, LocalDate userUserDob) {
		logger.info("UserAuthenticationServiceImpl :: updateUserDob -- begin");
		String successPrompt;
		Integer successCode = 0;
		try {
			successCode = userAuthenticationRepository.updateUserDob(userId, userUserDob);
			successPrompt = successCode == 0 ? "No rows updated" : "Successfully Updated";
		} catch (Exception e) {
			successPrompt = errorPrompt + e.getMessage();

		}
		logger.info("UserAuthenticationServiceImpl :: updateUserDob -- end");
		return successPrompt;
	}

}
