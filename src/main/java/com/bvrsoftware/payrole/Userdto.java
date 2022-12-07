package com.bvrsoftware.payrole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Userdto {
	private int uid;
	private String fullname;
	private String email;
	private String pass;
	private String role;
}
