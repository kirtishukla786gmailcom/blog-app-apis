
package com.code.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.code.blog.security.JwtAuthenticationEntryPoint;
import com.code.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig {

	public static final String [] PUBLIC_URLS= {"/api/v1/auth/**"
												,"/v3/api-docs"
												,"/v2/api-docs"
												,"/swagger-resources/**"
												,"/swagger-ui/**"
												,"/webjars/**"};
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAithenticationEntryPoint;
	/*
	 * @Bean public SecurityFilterChain filterSecurity(HttpSecurity http) throws
	 * Exception {
	 * http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().
	 * and().httpBasic(); return http.build(); }
	 */
	@Bean
	public SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests((authorize) -> 
				authorize
				.requestMatchers(PUBLIC_URLS).permitAll()
				.requestMatchers(HttpMethod.GET).permitAll()
				.anyRequest()
				.authenticated())
				.exceptionHandling().authenticationEntryPoint(jwtAithenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
