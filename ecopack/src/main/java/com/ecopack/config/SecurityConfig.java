package com.ecopack.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
    @Autowired
    DataSource dataSource;
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
					.antMatchers("/login","/css/**","/js/**","/templates/**").permitAll()
					.antMatchers("/api/admin/*").hasAuthority("ADMIN")
					.antMatchers("/api/user/*").hasAuthority("EMPLOYEE")
					//.antMatchers("/api/user/**").hasAnyAuthority("EMPLOYEE","ADMIN")
					.anyRequest().fullyAuthenticated()
				 .and()
				.formLogin()
					.loginPage("/login").failureUrl("/loginFailed")
					.defaultSuccessUrl("/")
					.usernameParameter("username").passwordParameter("password")
    			.and()
				//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    			.csrf()
    		    	.disable();
				
	}
    


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(
				"select user_name as username,user_password as password, is_active=1 as enabled from ecopack_user_master where user_name=?")
			.authoritiesByUsernameQuery(
				" SELECT user_name,roles.role as role"
			  + " FROM "
			  + " ecopack_employee_master emp,"
			  + " ecopack_user_master user,"
			  + " ecopack_user_roles roles"
			  + " WHERE "
			  + " emp.emp_id = user.emp_id AND "
			  + " emp.role_id = roles.role_id AND"
			  + " user.user_name=?");
				
	}
	
	//"select user_name as username,user_password as password, (case when (is_active = 'Yes') THEN 1 ELSE 0 END) as enabled from ecopack_user_master where user_name=?")
	
}
