package com.assignment.service;

import com.assignment.entity.UserAuthentication;
import java.time.LocalDate;
import java.util.List;

public interface UserAuthenticationService {

  String registerUser(
      String userName,
      String userEmail,
      LocalDate userDob,
      String userPassword,
      Boolean isSubscribed,
      Boolean isAdminAccount)
      throws Exception;

  List<UserAuthentication> loginInformationOfUser(String userName);

  String logUserOutOfSession(Long loggedInUserId);

  Long getLoggedInUser();

  String unSubscibeUser(Long loggedInSesionId);

  String logUserIntoTheSession(String userName);

  String retrieveAccount(String userName);

  List<UserAuthentication> retrieveInformationOfUser(String userName);
  
  List<UserAuthentication> getGeneralAccountsInfo();
  
  String deleteAccountWithUserId(Long userId);
  
  Boolean isAdminAccountById(Long userId);
  
  String updateUserName(Long userId, String userName);
  
  String updateUserEmail(Long userId, String userEmail);
  
  String updateUserPassword(Long userId, String userPassword);
  
  String updateUserDob(Long userId, LocalDate userUserDob);
  
}
