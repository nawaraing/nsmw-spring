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

	// 로그인 페이지로 이동하는 버튼을 눌렀을 때 기능
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginPage(HttpSession session) {

		session.removeAttribute("memberID");
		
		return "forward:/";
	}

}
