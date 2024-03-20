package com.naeddoco.nsmwspring.controller.commonController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardPageController {
	
	@GetMapping("/dashboard")
	public String dashboardPage() {
		
		System.out.println("[log] Controller dashboardPage요청");
		
		return "admin/dashboard";
		
	}
}