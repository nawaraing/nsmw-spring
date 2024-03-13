package com.naeddoco.nsmwspring.legacyController.async;

import java.io.IOException;
import java.io.PrintWriter;

import com.naeddoco.nsmwspring.legacyModel.dao.CartDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.ProductDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.CartDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.ProductDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/insertCart")
public class InsertCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertCart() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		System.out.println("[Log] InsertCart");
        
		/* Servlet InsertCart
        * 
        * 실재고와 유효성 검사
		*/
		
		// 장바구니에 담을 상품 받아오기 (필수)
		System.out.println("PID: " + request.getParameter("PID"));
		if (request.getParameter("PID") == null) {
			out.print("false");
			return;
		}
		String strPid = (String)request.getParameter("PID");
		int intPid = Integer.parseInt(strPid);
		
		// 장바구니에 담는 수량 받아오기 (선택)
		// 디폴트는 1
		int intQty = 1;
		if (request.getParameter("cQty") != null) {
			String strQty = (String)request.getParameter("cQty");
			intQty = Integer.parseInt(strQty);
		} else {
			System.out.println("[InsertCart] cQty is null");
		}
		System.out.println("cQty: " + intQty);
		
		// 실재고 유효성 검사
		// TODO: 메서드 빼는거 검토
		ProductDTO pDTO = new ProductDTO();
		ProductDAO pDAO = new ProductDAO();
		pDTO.setSearchCondition("상품상세정보");
		pDTO.setPID(intPid);
		pDTO = pDAO.selectOne(pDTO);
		if (intQty > pDTO.getpQty()) {
			System.out.println("재고 부족 cQty: " + intQty + "  pQty: " + pDTO.getpQty());
			out.print("false");
			return;
		}

		// 로그인한 사용자 받아오기 (필수)
		HttpSession session = request.getSession();
		System.out.println("member: " + session.getAttribute("member"));
		if (session.getAttribute("member") == null) {
			out.print("false");
			return;
		}
		String mid = (String)session.getAttribute("member");
		
		// 장바구니에 담기
		// 이미 담겨 있으면 update
		// 없으면 insert
		CartDTO cDTO = new CartDTO();
		CartDAO cDAO = new CartDAO();
		
		// 장바구니에 담겨 있는지 확인
		cDTO.setSearchCondition("상품확인");
		cDTO.setMID(mid);
		cDTO.setPID(intPid);
		cDTO = cDAO.selectOne(cDTO);
		System.out.println("selectOne cDTO: " + cDTO);
		
		if (cDTO == null) { // 장바구니에 없음
			cDTO = new CartDTO();
			cDTO.setSearchCondition("장바구니추가");
			cDTO.setMID(mid);
			cDTO.setPID(intPid);
			cDTO.setcQty(intQty);
//		System.out.println("cDAO.insert result: " + cDAO.insert(cDTO));
			if (cDAO.insert(cDTO)) {
				out.print("true");
			} else {
				out.print("false");
			}
		} else { // 장바구니에 있음
			cDTO.setSearchCondition("동일상품추가");
			cDTO.setcQty(intQty);
//			cDTO.setCid(cDTO.getCid()); // 이미 들어있음
			System.out.println("update cDTO: " + cDTO);
			if (cDAO.update(cDTO)) {
				System.out.println("update: true");
				out.print("true");
			} else {
				System.out.println("update: false");
				out.print("false");
			}
		}
		
		// V에 인증번호 전달
	}

}
