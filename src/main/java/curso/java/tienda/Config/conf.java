package curso.java.tienda.Config;

import org.springframework.context.MessageSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import curso.java.tienda.upload.storage.StorageProperties;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class conf {
	
	
	
	@Bean
	public MessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource message =  new ReloadableResourceBundleMessageSource();
		message.setBasename("classpath:errors");
		message.setDefaultEncoding("UTF-8");
		
		return message;
	}
	
	@Bean
    public MessageSource variables() {
        ReloadableResourceBundleMessageSource message =  new ReloadableResourceBundleMessageSource();
        message.setBasename("classpath:variables");
        message.setDefaultEncoding("UTF-8");
        return message;
    }
	
	@Bean
    public MessageSource spanish() {
        ReloadableResourceBundleMessageSource message =  new ReloadableResourceBundleMessageSource();
        message.setBasename("classpath:spanish");
        message.setDefaultEncoding("UTF-8");
        return message;
    }
	
	@Bean
    public MessageSource english() {
        ReloadableResourceBundleMessageSource message =  new ReloadableResourceBundleMessageSource();
        message.setBasename("classpath:english");
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
