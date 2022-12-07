package com.bvrsoftware.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bvrsoftware.jwt.JwtAuthenticationEntryPoint;
import com.bvrsoftware.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
    @Autowired
	private JwtAuthenticationFilter jwtFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtEntryPoint;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		  .csrf().disable()
		  .cors().disable()//is disable to cross browser 
		  .authorizeHttpRequests()
		  .antMatchers("/api/token").permitAll()
		  .anyRequest()
		  .authenticated()
		  .and()
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		  .and()
		  .exceptionHandling().authenticationEntryPoint(jwtEntryPoint);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailsService).passwordEncoder(this.passwordEncoder());
		//auth.inMemoryAuthentication().withUser("ssp9448").password(this.passwordEncoder().encode("12345")).roles("ADMIN");
		//auth.inMemoryAuthentication().withUser("sp9448").password(this.passwordEncoder().encode("12345")).roles("USER");
	}
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
