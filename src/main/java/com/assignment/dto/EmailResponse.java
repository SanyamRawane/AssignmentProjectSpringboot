package com.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailResponse {
  private String messageOfSentEmail;
  private Boolean statusOfSentEmail;
}
