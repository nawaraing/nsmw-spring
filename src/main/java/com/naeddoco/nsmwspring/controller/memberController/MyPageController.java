package com.naeddoco.nsmwspring.controller.memberController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
	
	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public String myPage(@ModelAttribute("modelInfo") MemberDTO memberDTO, HttpSession session) {
		
		System.out.println("[log] MyPageController 진입");
		
		String memberID = (String) session.getAttribute("memberID");
		
		System.out.println("[log] MyPageController 로그인 아이디 : " + memberID);
		
		return "user/myPage";
	}

}

