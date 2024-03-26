package com.example.demo.Config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class conf {
	
	@Bean
	public MessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource message =  new ReloadableResourceBundleMessageSource();
		message.setBasename("classpath:errors");
		message.setDefaultEncoding("UTF-8");
		
		return message;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

}
