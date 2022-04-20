package com.example.Web.Project;

import com.example.Web.Project.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> specificUrlPatternFilterRegistration () {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AuthFilter());
		registrationBean.addUrlPatterns("/project/getProject");
		registrationBean.addUrlPatterns("/project/chat");
		registrationBean.addUrlPatterns("/task/groupTask");
		registrationBean.addUrlPatterns("/task/individualTask");
		registrationBean.addUrlPatterns("/videoChat/getToken");
		registrationBean.addUrlPatterns("/user/userDetails");


		return registrationBean;
	}

}
