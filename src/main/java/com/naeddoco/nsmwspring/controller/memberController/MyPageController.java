package com.naeddoco.nsmwspring.controller.memberController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
	
	@RequestMapping(value = "/myPage", method = RequestMethod.POST)
	public String myPage(@ModelAttribute("modelInfo") MemberDTO memberDTO, HttpSession session) {
		
		String memberID = (String) session.getAttribute("memberID");
		
		return "user/myPage";
	}

}
