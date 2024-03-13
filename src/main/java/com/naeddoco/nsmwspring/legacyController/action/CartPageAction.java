package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.CartDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.CartDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class CartPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 장바구니페이지-장바구니 상품 조회 로직
		 */
		ActionForward forward = new ActionForward();
		CartDTO cDTO = new CartDTO();
		CartDAO cDAO = new CartDAO();
		HttpSession session = request.getSession();

		//세션에 로그인한 MID 가져오기
		String MID = (String)session.getAttribute("member");
		
		//DB에서 장바구니 목록 조회하여 가져오기
		cDTO.setMID(MID);
		cDTO.setSearchCondition("장바구니목록출력");
		ArrayList<CartDTO> cartList = cDAO.selectAll(cDTO);
//		System.out.println("[log] CartPageAction_cartList : "+ cartList);
		
		//조회된 장바구니목록(cartList) 전달
		request.setAttribute("cartList", cartList);
		
		//정보를 보낼 경로와 redirect방식 설정
		forward.setPath("cart.jsp");
		forward.setRedirect(false);

		return forward;
	}

}
