package com.bvrsoftware;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bvrsoftware.model.User;
import com.bvrsoftware.repositry.UserRepositry;

@SpringBootApplication
public class JwtTokenAuthenticationApplication implements CommandLineRunner
{
	@Autowired
	private UserRepositry repositry;
	
	@Autowired
	private PasswordEncoder encoder;
	public static void main(String[] args) {
		SpringApplication.run(JwtTokenAuthenticationApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		User u1=new User();
		u1.setEmail("ssp9448");
		u1.setFullname("sanjeev kumar");
		u1.setPass(this.encoder.encode("12345"));
		u1.setRole("ADMIN");
		repositry.save(u1);
		
		User u2=new User();
		u2.setEmail("rk9448");
		u2.setFullname("Rajeev kumar");
		u2.setPass(this.encoder.encode("12345"));
		u2.setRole("NORMAL");
		repositry.save(u2);
	}
}
