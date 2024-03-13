package com.naeddoco.nsmwspring.legacyKakao;

import java.io.IOException;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.MemberDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



public class KakaoLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 카카오계정 로그인 로직 
		 */
		ActionForward forward = new ActionForward();
		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();
		//V로부터 카카오ID받아오기
		String kakaoId = request.getParameter("kakaoId");
		
		//DB에서 해당 카카오ID가 존재하는지 확인
		mDTO.setSearchCondition("카카오로그인");
		mDTO.setKakaoId(kakaoId);
		mDTO = mDAO.selectOne(mDTO);
		
//		없는 계정이면 회원가입페이지로 이동
		if(mDTO == null) {
			request.setAttribute("kakaoId", kakaoId);
//			System.out.println("[log]kakaoId : " + kakaoId);
			forward.setPath("joinPage.do");
			forward.setRedirect(false);
		}
//		있는 계정이면 로그인 성공 -> 메인페이지로 이동
		else {
			HttpSession session = request.getSession();
			String MID = mDTO.getMID();
			//기존회원과 같이 세션으로 MID 전달
			session.setAttribute("member", MID);
//			System.out.println("[log]mid : " + MID);
			forward.setPath("mainPage.do");
			forward.setRedirect(false);
		}
		return forward;
	}

}
