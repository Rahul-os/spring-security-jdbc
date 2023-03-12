package com.javabrains.springsecurityjdbc;

import java.security.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {
	
	@Autowired
	DataSource datasource;
	
	@Bean
	public SecurityFilterChain jdbcauth(AuthenticationManagerBuilder auth) throws Exception
	{
		return auth.jdbcAuthentication()
				.dataSource(datasource);   //datasource obj is autowired.Spring boot will know and it maps that data source here.Here the datasource is H2 since we are using H2. Generally it can be any type of database.
//				.withDefaultSchema()
//				.withUser(
//						User.withUsername("user")
//							.password("user")
//							.roles("USER")
//						)
//				.withUser(
//						User.withUsername("admin")
//							.password("admin")
//							.roles("ADMIN")
//						);
			
		
					     //here we are manually adding 2 users,but generally the users and their autorities will be stored somewhere in the database, and from there we will access. So commenting these lines out and using the data from h2 database.
				//for that i am adding sql files with queries to insert data . Genrelly there is a default table structure spring provides .refer this link for the structure. "https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqa3dfZHJSYjVaWkN5OUJXdUdJYlRRZ09OV0w4d3xBQ3Jtc0ttT0JqMjJScFZXWTNqOFRlUFc3YzVxZ2tCclo4bXcxMjdYdkFyWVVQS1QxTWIxc1pOMTF2RTUxVkx2VHpWTl9HUVFMM2UtTFgwODN4Mk9HNlYwblV4cWYtTlRZUlFFNVRaT1ZkaXdTOVh4MFF0eDJJdw&q=https%3A%2F%2Fdocs.spring.io%2Fspring-security%2Fsite%2Fdocs%2Fcurrent%2Freference%2Fhtmlsingle%2F%23user-schema&v=LKvrFltAgCQ"
		
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
			.requestMatchers("/admin").hasRole("ADMIN")
			.requestMatchers("/user").hasAnyRole("USER","ADMIN")    //spring security doesnt know that it is admin ,when you dont include admin ad use hasrole() with only user, when you access url we cannot access admin , bcz spring security doesnt konw that .To make it known to spring we have to use hasAnyRole() and also add admin along with the user.
			.requestMatchers("/").permitAll()
			.and().formLogin();
		return http.build();
	}
}
