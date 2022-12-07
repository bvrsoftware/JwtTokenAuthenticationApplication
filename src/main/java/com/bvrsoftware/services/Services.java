package com.bvrsoftware.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.bvrsoftware.model.User;

@Service
public class Services {

	List<User> list=new ArrayList<>();
	 public Services() {
		 list.add(new User(1, "sanjeev", "ssp9448@gmail.com", "12345","ADMIN"));
		 list.add(new User(2, "rajeev", "rajeev@gmail.com", "12345","NORMAL"));
	 }
	 public List<User> getAllUser(){
		 return this.list;
	 }
	 public User getUser(int id) {
		 return this.list.stream().filter((u)->u.getUid()==id).findAny().orElse(null);
	 }
	 public User adduser(User user) {
		 list.add(user);
		 return user;
	 }
	 public User delete(User user) {
		 list.remove(user);
		 return user;
	 }
}
