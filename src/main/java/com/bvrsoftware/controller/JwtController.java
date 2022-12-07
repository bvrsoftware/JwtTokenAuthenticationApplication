package com.bvrsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bvrsoftware.jwt.helper.JwtUtil;
import com.bvrsoftware.model.JwtRequest;
import com.bvrsoftware.model.JwtResponse;
import com.bvrsoftware.security.CustomUserDetailsService;


@RestController
@RequestMapping(value = "/api")
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping(value = "/token")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest req){
		
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Bad credentials");
		}catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new BadCredentialsException("Bad credentials");
		}
		UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(req.getUsername());
		String token=this.jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
}
