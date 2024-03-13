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

public class ModifyUserPasswordAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 마이페이지-비밀번호 변경 로직
		 * 입력받은 비밀번호 일치시 비밀번호 업데이트
		 */
		ActionForward forward = new ActionForward();
		//세션의 MID와 입력받은 비밀번호 받아오기
		HttpSession session = request.getSession();
		String MID = (String) session.getAttribute("member");
		String MPW = request.getParameter("mPassword");
		
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		
		//DB에 접속하여 ID,PW update
		//일치하면 결과값 T/F 반환
		mDTO.setMID(MID);
		mDTO.setmPassword(MPW);
		mDTO.setSearchCondition("비밀번호변경");
		boolean result = mDAO.update(mDTO);
		
		if(result) {
			//비밀번호 변경시 계정 로그아웃 후 메인화면으로 이동
//			System.out.println("[log] 비밀번호변경 성공!");
			session.removeAttribute("member");
			forward.setPath("mainPage.do");
			//로그아웃시 main에 표시할 모달정보 전달
			request.setAttribute("logoutResult", true);
			forward.setRedirect(false);
		}else {
			//TODO: 모달로 비밀번호 변경 실패 안내 후 현재 페이지 잔류
//			System.out.println("[log] 비밀번호변경 실패");
		}
		
		return forward;
	}

}
