
package com.springboot.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.assignment.entity.User;
import com.springboot.assignment.repository.UserRepo;

@RestController
public class UserController {

	@Autowired
	private UserRepo userRepo;
	//4
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    //Handler for save
	@PostMapping("/save-user")
	public User addUser(@RequestBody User user) {

		//5
		user.setPassword(encoder.encode(user.getPassword()));
		this.userRepo.save(user);
		return user;
	}

}
