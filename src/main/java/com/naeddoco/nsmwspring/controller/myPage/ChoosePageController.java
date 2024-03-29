package com.naeddoco.nsmwspring.controller.myPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChoosePageController {
	
	@Autowired
	private MemberService memberService;
	
	// 사용자가 입력한 버튼값에 따른 페이지 이동
	@RequestMapping(value = "/user/updateUserInfo", method = RequestMethod.POST)
	public String changeInfoPage(MemberDTO memberDTO, Model model, HttpSession session, @RequestParam("where") String where) {
		
		System.out.println("[changeInfoPage] 진입");
		
		System.out.println("where의 값 : " + where);
		
		// 비밀번호를 제외한 회원정보 변경
		if(where.equals("modifyUserInfo")) {
			
			System.out.println("[modifyUserInfo] 진입");
			
			// 처음에 로드될 때 변경할 정보들 보여줘야함
			// 이름
			// 전화번호 (3등분)
			// 이메일 (2등분)
			// 주소 (3등분)
			
			String memberID = (String) session.getAttribute("memberID");
			
			memberDTO.setSearchCondition("myPageMain");
			memberDTO.setMemberID(memberID);
			memberDTO = memberService.selectOne(memberDTO);
			
			// 회원의 전화번호
			System.out.println("[회원정보변경] 회원ID로 검색한 회원 전화번호 " + memberDTO.getPhoneNumber());
	        String phoneNumber = memberDTO.getPhoneNumber();
	        
	        // 좌 우 공백제거
	        phoneNumber = phoneNumber.trim();
	        
	        System.out.println("[회원정보변경] 회원ID로 검색한 회원 전화번호 좌우 공백제거 " + phoneNumber);
	        
	        // 전화번호 분리 후 저장
	        String phoneNumber1 = phoneNumber.substring(0, 3);
	        String phoneNumber2 = phoneNumber.substring(4, 8);
	        String phoneNumber3 = phoneNumber.substring(9);
			System.out.println("[회원정보변경][전화번호 前] " + phoneNumber1);
			System.out.println("[회원정보변경][전화번호 中] " + phoneNumber2);
			System.out.println("[회원정보변경][전화번호 後] " + phoneNumber3);
	        
	        // 회원의 이메일
	        String email = memberDTO.getEmail();
	        
	        // @의 위치(index 번호)
	        int atIndex = email.indexOf('@');
	        
	        // @부터 앞의 문자열
	        String email1 = email.substring(0, atIndex);
	        // @부터 뒤의 문자열
	        String email2 = email.substring(atIndex + 1);
			
			model.addAttribute("memberInfo", memberDTO);
			model.addAttribute("phoneNumber1", phoneNumber1);
			model.addAttribute("phoneNumber2", phoneNumber2);
			model.addAttribute("phoneNumber3", phoneNumber3);
			model.addAttribute("email1", email1);
			model.addAttribute("email2", email2);
			
			model.addAttribute("memberInfo", memberDTO)	;	
			
			System.out.println("[회원정보변경] 로드 시 출력될 회원 정보 : " + memberDTO.toString());
			
			model.addAttribute("pageValue", "개인정보수정");
			
			return "user/modifyUserInfo";
			
		}
		// 회원 비밀번호 변경
		else if(where.equals("modifyUserPassword")) {
			
			System.out.println("[modifyUserPassword] 진입");
			
			// 정보없이 이동해서 거기서 세션에서 memberID 입력받아서 사용자가 입력한 pw가 유효성 검사를 통과하면 변경
			
			model.addAttribute("pageValue", "비밀번호변경");
			
			return "user/modifyUserPassword";
			
		}

		return "user/error";
	}

}
