package com.shubbi.reactblogbackend;

import com.shubbi.reactblogbackend.users.User;
import com.shubbi.reactblogbackend.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactBlogBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReactBlogBackendApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.save(new User("Shubham Sharma", "sharmashubham850@gmail.com", "shubbi850", "sharma"));
	}
}
