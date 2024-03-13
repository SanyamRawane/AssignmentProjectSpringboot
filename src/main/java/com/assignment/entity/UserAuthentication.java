package com.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

@Table(name = "userAuthentication")
@Data
@Entity
public class UserAuthentication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long userAuthenticationId;

  @Column(unique = true)
  String userAuthenticationName;

  String userAuthenticationEmail;

  LocalDate userAuthenticationDOB;

  String userAuthenticationPassword;

  Boolean isSubscribed;

  Boolean isUserLoggedIn;
  
  Boolean isAdminAccount;
}
