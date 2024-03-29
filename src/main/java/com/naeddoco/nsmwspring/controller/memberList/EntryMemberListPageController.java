package com.naeddoco.nsmwspring.controller.memberList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class EntryMemberListPageController {
	
	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String entryCart(HttpSession session, Model model) {

		//-----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에 있는 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		//-----------------------------------------------세션 확인 ↓-----------------------------------------------
		
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setSearchCondition("selectAdminMemberListDatas"); // 쿼리 분기명 set
		memberDTO.setSearchKeyword("%%"); // 검색 키워드 set
		memberDTO.setSortColumnName("MEMBER_ID"); // 정렬할 컬럼명 set
		memberDTO.setSortMode("ASC"); // 정렬 방식 set
		
		
		return "admin/memberList"; // 장바구니 페이지로 요청

	}

}
