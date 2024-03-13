package com.assignment.dto;

import lombok.Data;

@Data
public class EmailRequest {

  private String nameOfEmailOwner;
  private String toEmaiId;
  private String fromEmailId;
  private String subjectOfEmail;
}
