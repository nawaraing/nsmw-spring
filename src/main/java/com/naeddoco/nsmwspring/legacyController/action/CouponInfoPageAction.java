package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.CouponDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.CouponDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CouponInfoPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 마이페이지-쿠폰목록 조회 로직
		 * 쿠폰목록 조회시 미사용_기간만료 쿠폰 삭제하고 조회하기
		 */
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		CouponDTO cpDTO = new CouponDTO();
		CouponDAO cpDAO = new CouponDAO();
		
		//세션의 MID가져오기
		String MID = (String)session.getAttribute("member");
		
		//미사용_기간만료 쿠폰 삭제
		cpDTO.setMID(MID);
		cpDTO.setSearchCondition("쿠폰삭제");
		boolean result = cpDAO.delete(cpDTO);
		
		if(result) {
//			System.out.println("[log] 기간만료_미사용쿠폰 삭제 완료!");
		}else {
//			System.out.println("[log] 기간만료_미사용쿠폰 없음");
		}
		
		//DB의 쿠폰목록조회 후 couponList에 담기
		cpDTO.setSearchCondition("쿠폰목록");
		ArrayList<CouponDTO> couponList = cpDAO.selectAll(cpDTO);
		
		//조회된 쿠폰목록(couponList) 전달
		request.setAttribute("couponList", couponList);
//		System.out.println("[log] CouponInfoPageAction couponList ["+ couponList +"]");
		
		//정보를 보낼 경로와 redirect방식 설정
		forward.setPath("couponInfo.jsp");
		forward.setRedirect(false);

		return forward;
	}

}
