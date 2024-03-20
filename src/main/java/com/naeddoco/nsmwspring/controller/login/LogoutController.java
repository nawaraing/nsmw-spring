package com.naeddoco.nsmwspring.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberModel.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginPage(HttpSession session) {

		session.removeAttribute("memberID"); // 세션에서 유저 아이디 삭제
		
		return "redirect:/";
		
	}

}