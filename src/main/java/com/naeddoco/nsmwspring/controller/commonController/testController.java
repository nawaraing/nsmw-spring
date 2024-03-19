package com.naeddoco.nsmwspring.controller.commonController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class testController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {
		
		System.out.println("[log] Controller root요청");
		
		return "user/index";
		
	}
}