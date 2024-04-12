package com.naeddoco.nsmwspring.controller.memberList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberModel.MemberService;
import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 회원목록에 진입하는 컨트롤러

@Controller
@Slf4j
public class EntryMemberListPageController {
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String entryCart(HttpSession session, Model model) {

		//-----------------------------------------------세션 확인 ↓-----------------------------------------------

		String authority = (String) session.getAttribute("authority");
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (!authority.equals("ADMIN")) {
			
			log.debug("회원 등급이 관리자(ADMIN)가 아니여서 메인페이지로 이동");
			
			return "redirect:/";
		}	
		
		//-----------------------------------------------회원 정보 습득 ↓-----------------------------------------------
		
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setSearchCondition("selectAdminMemberListDatas"); // 쿼리 분기명 set
		memberDTO.setSearchKeyword("%%"); // 검색 키워드 set
		memberDTO.setSortColumnName("M.MEMBER_ID"); // 정렬할 컬럼명 set
		memberDTO.setSortMode("ASC"); // 정렬 방식 set
		
		List<MemberDTO> memberDTOList = memberService.selectAll(memberDTO);
		
		model.addAttribute("memberList", memberDTOList);
		
		return "admin/memberList"; // 장바구니 페이지로 요청

	}

}
