package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.MemberDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ModifyUserInfoPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 마이페이지-회원정보변경페이지-로그인한 회원정보 출력 로직
		 */
		ActionForward forward = new ActionForward();
		//세션의 MID 가져오기
		HttpSession session = request.getSession();
		String MID = (String)session.getAttribute("member");
		
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		//로그인된 회원정보 DB에서 조회
		mDTO.setMID(MID);
		mDTO.setSearchCondition("회원정보");
		mDTO = mDAO.selectOne(mDTO);
//		System.out.println("[log]ModifyUserInfoPageAction mDTO [" + mDTO + "]");
		
		//조회된 회원정보 보내주기
		request.setAttribute("memberInfo", mDTO);
		//정보를 보낼 경로와 redirect방식 설정
		//개인정보수정 화면으로 이동
		forward.setPath("modifyUserInfo.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
