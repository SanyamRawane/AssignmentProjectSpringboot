package com.assignment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class TemplateEmailConfiguration {

  @Primary
  @Bean
  FreeMarkerConfigurationFactoryBean markerConfigurationFactoryBean() {

    FreeMarkerConfigurationFactoryBean configurationFactoryBean =
        new FreeMarkerConfigurationFactoryBean();
    configurationFactoryBean.setTemplateLoaderPath("classpath:/templates");
    return configurationFactoryBean;
  }
}
