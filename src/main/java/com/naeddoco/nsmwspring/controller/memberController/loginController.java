package com.naeddoco.nsmwspring.controller.memberController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/login")
	public String test(@ModelAttribute("member") MemberDTO memberDTO, Model model, HttpSession session) {
		
		// 쿼리문 실행결과를 memberDTO에 저장
		memberDTO = memberService.selectOne(memberDTO);

		// memberDTO의 값에 따른 return값 지정
		// memberDTO의 값이 있다면?
		if (memberDTO != null) {
			System.out.println("[log] Controller 로그인 성공");

			// 회원이 탈퇴상태가 아니라면
			if (!memberDTO.getMemberState().equals("GHOST")) {

				// 로그인 성공했고 회원 권한이 USER일 때
				if (memberDTO.getAuthority().equals("USER")) {
					System.out.println("[log] Controller USER 로그인");
					
					session.setAttribute("memberID", memberDTO.getMemberID());

					model.addAttribute("msg", memberDTO.getMemberName()+"님 환영합니다");
					// 메인페이지로 이동
					return "main";              

					// 로그인에 성공했지만 회원 권한이 USER가 아닐때
					// 오류로인한 관리자페이지 이동을 막기위한 else if 사용
				} else if (memberDTO.getAuthority().equals("ADMIN")) {
					System.out.println("[log] Controller Admin 로그인");                 
					
					session.setAttribute("memberID", memberDTO.getMemberID());

					model.addAttribute("msg", memberDTO.getMemberName()+"관리자님 환영합니다");
					// 관리자 메인페이지로 이동
					return "adminMain";
				}			
			}
			
			// 회원이 탈퇴상태라면 
			model.addAttribute("mag", "탈퇴한 회원입니다");
			return "goBack";
		}

		// 로그인 실패 메세지 전달
		model.addAttribute("msg", "일치하는 회원이 없습니다");
		// 로그인 실패로 다시 goBack.jsp 페이지로 이동
		return "goBack";
	}
}