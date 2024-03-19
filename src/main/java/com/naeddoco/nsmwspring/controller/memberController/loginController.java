package com.naeddoco.nsmwspring.controller.memberController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {

	@Autowired
	private MemberService memberService;

	// 로그인 페이지로 이동하는 버튼을 눌렀을 때 기능
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(@ModelAttribute("member") MemberDTO memberDTO, Model model, HttpSession session) {

		// 로그인 페이지 로그
		System.out.println("[log] Controller 로그인 페이지 이동");

		return "login";
	}

	// 회원 정보를 모두 입력한 후 로그인 버튼의 기능
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("member") MemberDTO memberDTO, Model model, HttpSession session) {

		// 정보 입력 후 로그인버튼 클릭 시 기능
		System.out.println("[log] Controller 로그인 버튼 클릭");

		// 입력된 ID와 PW
		System.out.println(
				"[log] Controller ID 및 PW\n[ID]" + memberDTO.getMemberID() + "\n[PW]" + memberDTO.getMemberPassword());

		// 쿼리문 실행결과를 memberDTO에 저장
		memberDTO.setSearchCondition("memberLogin");
		memberDTO = memberService.selectOne(memberDTO);

		// selectOne 결과 로그
		System.out.println(
				"[log] Controller 로그인 결과\n[ID]" + memberDTO.getMemberID() + "\n[등급]" + memberDTO.getAuthority());

		// memberDTO의 값에 따른 return값 지정
		// memberDTO의 값이 있다면?
		if (memberDTO != null) {
			System.out.println("[log] Controller 로그인 성공");

			// 로그인 성공했고 회원 권한이 USER일 때
			if (memberDTO.getAuthority().equals("USER")) {
				System.out.println("[log] Controller USER 로그인");

				session.setAttribute("memberID", memberDTO.getMemberID());

				model.addAttribute("msg", memberDTO.getMemberName() + "님 환영합니다");
				// 메인페이지로 이동
				return "main";

				// 로그인에 성공했지만 회원 권한이 USER가 아닐때
				// 오류로인한 관리자페이지 이동을 막기위한 else if 사용
			} else if (memberDTO.getAuthority().equals("ADMIN")) {
				System.out.println("[log] Controller Admin 로그인");

				session.setAttribute("memberID", memberDTO.getMemberID());

				model.addAttribute("msg", memberDTO.getMemberName() + "관리자님 환영합니다");
				// 관리자 메인페이지로 이동
				return "dashboard";
			}
		}

		System.out.println("[log] Controller 일치하는 회원 없음");

		// 로그인 실패 메세지 전달
		model.addAttribute("msg", "일치하는 회원이 없습니다");
		// 로그인 실패로 다시 goBack.jsp 페이지로 이동
		return "main";
	}
}