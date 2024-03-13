package com.assignment.serviceimpl;

import com.assignment.dto.EmailRequest;
import com.assignment.dto.EmailResponse;
import com.assignment.service.EmailSubscriptionService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class EmailSubscriptionServiceImpl implements EmailSubscriptionService {

  @Autowired private JavaMailSender mailSender;

  @Autowired private Configuration configuration;

  private Logger logger = LoggerFactory.getLogger(EmailSubscriptionServiceImpl.class);

  /**
   * This method sends the email with given id, subject and body
   *
   * @param mailItTo, mailSubject, mailBody :- Details to locate the target user send email
   * @return successPrompt :- prompt string indicating the success status.
   */
  @Override
  public String sendEmailToSubscriber(String mailItTo, String mailSubject, String mailBody) {
    logger.info("EmailSubscriptionServiceImpl :: sendEmailToSubscriber -- begin");
    String successPrompt;

    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(mailItTo);
    simpleMailMessage.setSubject(mailSubject);
    simpleMailMessage.setText(mailBody);
    logger.info("EmailSubscriptionServiceImpl :: sendEmailToSubscriber -- Before Try");
    try {
      mailSender.send(simpleMailMessage);
      successPrompt = "Mail Sent Successfully!";

    } catch (Exception e) {
      successPrompt = "Error Sending Mail";
    }

    logger.info("EmailSubscriptionServiceImpl :: sendEmailToSubscriber -- end");

    return successPrompt;
  }

  @Override
  public EmailResponse sendEmailAsTemplate(EmailRequest request, Map<String, Object> model) {

    logger.info("EmailSubscriptionServiceImpl :: sendEmailAsTemplate -- begin");

    EmailResponse response = new EmailResponse();
    MimeMessage message = mailSender.createMimeMessage();
    try {
      // set mediaType
      MimeMessageHelper helper =
          new MimeMessageHelper(
              message,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      // add attachment
      helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

      Template t = configuration.getTemplate("emailTemplateVersionOne.ftl");
      String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

      helper.setTo(request.getToEmaiId());
      helper.setText(html, true);
      helper.setSubject(request.getSubjectOfEmail());
      helper.setFrom(request.getFromEmailId());
      mailSender.send(message);

      response.setMessageOfSentEmail("mail send to : " + request.getToEmaiId());
      response.setStatusOfSentEmail(Boolean.TRUE);

    } catch (MessagingException | IOException | TemplateException e) {
      response.setMessageOfSentEmail("Mail Sending failure : " + e.getMessage());
      response.setStatusOfSentEmail(Boolean.FALSE);
    }
    logger.info("EmailSubscriptionServiceImpl :: sendEmailAsTemplate -- end");
    return response;
  }
}
