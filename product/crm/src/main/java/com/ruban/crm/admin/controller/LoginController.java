package com.ruban.crm.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {

	private String a = "1";
	
	public LoginController() {
		System.out.println(a);
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login() {
		System.out.println("dsjjjjjjjjjj");
		
		return "login";
	}
}
