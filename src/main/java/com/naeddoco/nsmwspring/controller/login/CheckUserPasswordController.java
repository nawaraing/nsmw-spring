package com.naeddoco.nsmwspring.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CheckUserPasswordController {

	@Autowired
	private MemberService memberService;
	
	/*
	 * 마이페이지-개인정보수정/비밀번호변경 선택시 현재 비밀번호를 입력하는 페이지로 이동
	 */
	
	@GetMapping("/checkUserPassword")
	public String checkUserPasswordPage() {

		return "checkUserPassword";
		
	}

	/*
	 * 마이페이지-개인정보수정/비밀번호변경 선택시 현재 비밀번호 일치여부 확인 로직
	 */
	
	@PostMapping("/checkUserPassword")
	public String CheckUserPassword (@RequestParam String where, MemberDTO memberDTO, Model model) {	
		
		// V에서 입력에 따른 이동 경로
		String getPath = where;
		
		memberDTO.setSearchCondition("비밀번호확인");	
		memberDTO = memberService.selectOne(memberDTO);
		
		// 일치하는 row가 있다면
		if(memberDTO != null) {
			
			log.debug("[log] CheckUserPasswordController 비밀번호 일치!");
			
			// where값에 따른 경로 설정
			if (getPath.equals("modifyUserInfo")) {

				// 회원정보 변경으로 이동
				return "modifyUserInfoPage";
			
			} else if (getPath.equals("modifyUserPassword")) {

				// 비밀번호 변경으로 이동
				return "modifyUserPasswordPage";			
			}

			// 에러페이지로 이동
			return "error";
		}
		
		// 일치하는 row가 없다면

		log.debug("[log] CheckUserPasswordController 비밀번호 불일치");
		
		// 비밀번호 불일치 결과 전달
		model.addAttribute("checkResult", false);
		
		// 비밀번호확인 페이지로 이동
		return "checkUserPasswordPage";
	}
	
}
