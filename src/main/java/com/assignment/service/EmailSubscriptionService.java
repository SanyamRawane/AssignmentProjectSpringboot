package com.assignment.service;

import com.assignment.dto.EmailRequest;
import com.assignment.dto.EmailResponse;
import java.util.Map;

public interface EmailSubscriptionService {

  public String sendEmailToSubscriber(String to, String subject, String body);

  public EmailResponse sendEmailAsTemplate(EmailRequest request, Map<String, Object> model);
}
