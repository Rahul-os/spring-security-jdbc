package com.javabrains.springsecurityjdbc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

	@GetMapping("/")
	public String home()
	{
		return("<h1>Welcome</h1>");
	}
	
	@GetMapping("/user")
	public String useraccess()
	{
		return("<h1>User successfully logged in!!");
	}
	
	@GetMapping("/admin")
	public String adminaccess()
	{
		return("<h1>Admin successfully logged in!!");
	}
}
