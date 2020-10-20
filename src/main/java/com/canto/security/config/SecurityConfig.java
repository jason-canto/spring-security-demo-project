package com.canto.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.userDetailsService(userDetailsService); }
	 */

	@Autowired
	private DataSource dataSource;

	/**
	 * Using a personalized schema with a specific query
	 */
	private static final String USER_QUERY =
			"select username, password, enabled "
			+ "from users "
			+ "where username = ?";
	private static final String AUTH_QUERY =
			"select username, authority "
			+ "from authorities "
			+ "where username = ?";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(USER_QUERY)
			.authoritiesByUsernameQuery(AUTH_QUERY);
	}

	/**
	 * Configuration for the authorization example
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/user").hasAnyRole("USER", "ADMIN")
			.antMatchers("/admin").hasRole("ADMIN")
			.and().formLogin();
	}

	/**
	 * With {@link HttpSecurity} permitAll, requests will be allowed to be accessed 
	 * from the Spring Security Filter Chain. This is costly as requests that come 
	 * into this filter chain needs to be allowed or disallowed based on Authentication/Authorization.
	 * 
	 * With {@link WebSecurity#ignoring()} any requests to resources will completely
	 * by pass the Spring Security Filter Chain all together. 
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/resources/**")
			.antMatchers("/publics/**");
	}

	/**
	 * Definition of the Password Encoder so that we don't store the password in plaintext
	 * @return PasswordEncoder encoded password
	 */
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
