package com.naeddoco.nsmwspring.controller.memberController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;

@Controller
public class loginController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/")
	public String test(Model model, MemberDTO memberDTO) {
		model.addAttribute("datas", memberService.selectAll(memberDTO));
		return "index";
	}
}
