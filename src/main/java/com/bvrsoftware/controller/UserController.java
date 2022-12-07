package com.bvrsoftware.controller;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bvrsoftware.model.User;
import com.bvrsoftware.payrole.Userdto;
import com.bvrsoftware.repositry.UserRepositry;

@RestController
@RequestMapping(value="/api")
public class UserController {

	@Autowired
	private UserRepositry repositry;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private ModelMapper mapper;

	@PostMapping(value = "/register")
	public ResponseEntity<Userdto> save(@RequestBody Userdto udto){
		User user=mapper.map(udto, User.class);
		user.setPass(encoder.encode(udto.getPass()));
		repositry.save(user);
		return new ResponseEntity<Userdto>(mapper.map(user, Userdto.class),HttpStatus.CREATED);
	}
	@PutMapping(value = "/user/{id}")
	public ResponseEntity<Userdto> update(@RequestBody Userdto udto,@PathVariable Integer id){
		User user=repositry.findById(id).get();
		if(user!=null) {
         user.setEmail(udto.getEmail());
         user.setFullname(udto.getFullname());
         user.setPass(udto.getPass());
		repositry.save(user);
		}
		return new ResponseEntity<Userdto>(mapper.map(user, Userdto.class),HttpStatus.CONFLICT);
	}
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<?> getbyid(@PathVariable Integer id){
		User user=repositry.findById(id).get();
		if(user==null) 
			return new ResponseEntity<>("Record not found !!",HttpStatus.OK);
		else
		    return new ResponseEntity<Userdto>(mapper.map(user, Userdto.class),HttpStatus.OK);
	}
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		User user=repositry.findById(id).get();
		if(user!=null) { 
			repositry.delete(user);
			return new ResponseEntity<>("delete successfully !!",HttpStatus.OK);
		}
		else {
		     return new ResponseEntity<>("record not found",HttpStatus.OK);
		}
	}
	@GetMapping(value= "/user")
	public ResponseEntity<List<Userdto>> getAll(){
	         List<User> list=repositry.findAll();
	         List<Userdto> dtolist=list.stream().map(l->this.mapper.map(l, Userdto.class)).collect(Collectors.toList());
		     return new ResponseEntity<>(dtolist,HttpStatus.OK);
	}
}
