package com.assignment.repository;

import com.assignment.entity.UserAuthentication;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {
	/**
	 * This method registers new user into the database
	 *
	 * @param userName, userEmail ,userDob ,userPassword, isSubscribed, isLoggedIn -
	 *                  data for sign up process
	 * @return number of rows affected in the database table
	 */
	@Transactional
	@Modifying
	@Query(value = "insert into user_authentication (user_authenticationdob,user_authentication_email,user_authentication_name,user_authentication_password,is_subscribed,is_user_logged_in,is_admin_account) values( :userDob, :userEmail, :userName, :userPassword, :isSubscribed, :isLoggedIn, :isAdminAccount)", nativeQuery = true)
	Integer registerUser(@Param("userName") String userName, @Param("userEmail") String userEmail,
			@Param("userDob") LocalDate userDob, @Param("userPassword") String userPassword,
			@Param("isSubscribed") Boolean isSubscribed, @Param("isLoggedIn") Boolean isLoggedIn,
			@Param("isAdminAccount") Boolean isAdminAccount);

	@Query(value = "select * from user_authentication where user_authentication_name= :userName and is_subscribed=1", nativeQuery = true)
	List<UserAuthentication> loginInformationOfUser(@Param("userName") String userName);

	/**
	 * This query updates user_authentication and sets the log in status as true for
	 * matching email id of user
	 *
	 * @param userEmail : It is the email id of the user
	 * @return This query returns number of records updated (zero for failure and
	 *         non-zero for success)
	 */
	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = "update user_authentication set is_user_logged_in=1 where user_authentication_id in (select user_authentication_id from user_authentication where user_authentication_name=:userName);", nativeQuery = true)
	public Integer logUserInSession(@Param("userName") String userName);

	/**
	 * This query updates the record in user_authentication table with matching id
	 * of user and sets logged in status of user as FALSE
	 *
	 * @param loggedInSesionId : Id of the user logged into the session
	 * @return This query returns number of records updated (zero for failure and
	 *         non-zero for success)
	 */
	@Transactional
	@Modifying
	@Query(value = "update user_authentication set is_user_logged_in=0 where user_authentication_id= :loggedInSesionId", nativeQuery = true)
	public Integer logOutSession(@Param("loggedInSesionId") Long loggedInSesionId);

	/**
	 * This query selects the record which is logged into the session
	 *
	 * @param none : This query does not take any parameter
	 * @return This query returns the id of user (record) which is logged into the
	 *         session
	 */
	@Query(value = "select user_authentication_id from user_authentication where is_user_logged_in=1 and is_subscribed=1", nativeQuery = true)
	public Long getLoggedInSession();

	@Transactional
	@Modifying
	@Query(value = "update user_authentication set is_subscribed=0,is_user_logged_in=0 where user_authentication_id= :loggedInSesionId", nativeQuery = true)
	public Integer unsubscribeUser(@Param("loggedInSesionId") Long loggedInSesionId);

	@Transactional
	@Modifying
	@Query(value = "update user_authentication set is_subscribed=1,is_user_logged_in=1 where user_authentication_name= :userName", nativeQuery = true)
	public Integer retrieveAccount(@Param("userName") String userName);

	@Query(value = "select * from user_authentication where user_authentication_name= :userName", nativeQuery = true)
	List<UserAuthentication> retrieveInformationOfUser(@Param("userName") String userName);

	@Query(value = "select * from user_authentication where is_admin_account=0", nativeQuery = true)
	List<UserAuthentication> getGeneralAccountsInfo();

	@Transactional
	@Modifying
	@Query(value = "delete from user_authentication where user_authentication_id= :userId and is_admin_account='false'", nativeQuery = true)
	Integer deleteAccountWithUserId(@Param("userId") Long userId);

	@Query(value = "select is_admin_account from user_authentication where user_authentication_id= :userId", nativeQuery = true)
	Boolean isAdminAccountById(@Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query(value = "update user_authentication set user_authentication_password= :userPassword where user_authentication_id= :userId", nativeQuery = true)
	Integer updateUserPassword(@Param("userId") Long userId, @Param("userPassword") String userPassword);

	@Transactional
	@Modifying
	@Query(value = "update user_authentication set user_authentication_name= :userName where user_authentication_id= :userId", nativeQuery = true)
	Integer updateUserName(@Param("userId") Long userId, @Param("userName") String userName);

	@Transactional
	@Modifying
	@Query(value = "update user_authentication set user_authentication_email= :userEmail where user_authentication_id= :userId", nativeQuery = true)
	Integer updateUserEmail(@Param("userId") Long userId, @Param("userEmail") String userEmail);

	@Transactional
	@Modifying
	@Query(value = "update user_authentication set user_authenticationdob= :userDob where user_authentication_id= :userId", nativeQuery = true)
	Integer updateUserDob(@Param("userId") Long userId, @Param("userDob") LocalDate userDob);

}
