package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.util.ArrayList;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.ProductDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.ReviewDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.ProductDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.ReviewDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductAllPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		ArrayList<ProductDTO> pDTOs = new ArrayList<ProductDTO>();
		ProductDTO pDTO = new ProductDTO();
		ProductDAO pDAO = new ProductDAO();
		
		/* 
		 * model에서 필터 적용한 상품 가져오기
		 */
		
		// [준현] 24.02.05
		// sellingPrice 디폴트 값이 최댓값->0으로 변경
//		// 전체 상품 중 최대 가격을 가져와서 price 변수를 초기화
//		pDTO.setSearchCondition("최대값");
//		pDTO = pDAO.selectOne(pDTO);
//		int price = pDTO.getSellingPrice();
		
		// 필터링해서 상품들 가져오기
		pDTO.setSearchCondition("상품출력필터");
		pDTO.setAncSelectMin(1);
		pDTO.setAncSelectMax(100);
		
		String searchName = null;
		if (request.getParameter("searchName") != null) {
			searchName = (String)request.getParameter("searchName");
		}
		System.out.println("searchName : " + searchName);
		pDTO.setpName(searchName);
		
		String category = null;
		if (request.getParameter("category") != null) {
			category = (String)request.getParameter("category");
		}
		System.out.println("category : " + category);
		pDTO.setCategory(category);
		
		int price = 0;
		System.out.println("[ProductAllPage] price : " + request.getParameter("price"));
		if (request.getParameter("price") != null) {
			price = Integer.parseInt((String)request.getParameter("price"));
		}
		System.out.println("price : " + price);
		pDTO.setSellingPrice(price);
//		pDTO.setSellingPrice(0);
		
		pDTOs = pDAO.selectAll(pDTO);
		
		// 보여줄 상품이 하나도 없는 경우
		if (pDTOs == null) {
//			request.setAttribute("pDTOs", pDTOs);
			
			forward.setPath("productAll.jsp");
			forward.setRedirect(false);
			
			return forward;
		}
		
		// 각 상품의 평균 별점 설정
		for (ProductDTO productDTO : pDTOs) {
			int pid = productDTO.getPID();
			int avgRating = getAverageRating(pid);
			productDTO.setAncAvgRating(avgRating);
//			System.out.println("[log] PID : " + productDTO.getPID());
//			System.out.println("[log] pDTOs 평균별점 " + productDTO.getAncAvgRating());
		}
		
		/* 
		 * 전체 페이지 수 확인
		*/
		
		System.out.println("[ProductAllPage] pDTOs.size : " + pDTOs.size());
		int totalPages = pDTOs.size() / 9;
		if (pDTOs.size() % 9 != 0) {
			totalPages++;
		}
		request.setAttribute("totalPages", totalPages);
		
		/* 
		 * 상품 목록 9개 띄우기
		*/
		
		// 뷰에서 넘겨준 page
		String strPage = request.getParameter("page");
		System.out.println("[ProductAllPage] String strPage : " + strPage);
		int intPage = 1;
		
		// strPage == null 인 상황은, (메인 페이지 -> 전체 목록 페이지) 처음 올 때! 
		if (strPage != null) {
			intPage = Integer.parseInt(strPage);
		}
		
		// page가 유효한 범위를 벗어남
		if (intPage < 1 || intPage > totalPages) {
			forward.setPath("error.do");
			forward.setRedirect(true);
			
			return forward;
		}
		
		// retDTOs : 뷰에게 넘겨준 DTO들이고
		// pDTOs : 모델에서 가져온 필터가 적용된 모든 상품들 (9개 이상일 수 있음)
		ArrayList<ProductDTO> retDTOs = new ArrayList<ProductDTO>();
		int startProduct = (intPage - 1) * 9 + 1;
		int endProduct = (intPage - 1) * 9 + 9;
		
		// 마지막 페이지에서 상품이 9개가 안 될 경우 인덱스 에러가 날 수 있어서 방지!
		if (endProduct > pDTOs.size()) {
			endProduct = pDTOs.size();
		}
		
		// 해당 페이지에 포함되는 제품 9개 담아줌
		System.out.println("startProduct index: " + startProduct);
		System.out.println("endProduct index: " + endProduct);
		for (int i = startProduct - 1; i < endProduct; i++) {
			retDTOs.add(pDTOs.get(i));
			System.out.println("[ProductAllPage] pDTOs[" + i + "] : " + pDTOs.get(i));
		}
//		System.out.println("[ProductAllPage] for end");
		System.out.println("[log] retDTOs : " + retDTOs);
		request.setAttribute("pDTOs", retDTOs);

		forward.setPath("productAll.jsp");
		forward.setRedirect(false);
		System.out.println("[ProductAllPage] forward: " + forward.toString());
		return forward;
	}

	// [준현] 24.02.05
	// 미사용 확인 후 주석 처리
//	public static boolean isNumber(String strValue) {
//		return strValue != null && strValue.matches("[-+]?\\d*\\.?\\d+");
//	}
	
	/*
 	상품의 평균 별점을 가져오는 메서드
	 */
	private int getAverageRating(int pid) {
		System.out.println("getAverageRating 메서드 진입");
		ReviewDTO rDTO = new ReviewDTO();
		ReviewDAO rDAO = new ReviewDAO();

		rDTO.setAncPID(pid);
		rDTO.setSearchCondition("별점평균");

		rDTO = rDAO.selectOne(rDTO);

		if (rDTO != null) {
			// 평균값이 있다면, int 평균 별점 값을 반환
			return (int) rDTO.getAncAvgScore();
		} else {
			// 평균 별점이 없을 경우 기본값 반환
			return 0;
		}
	}
	
}
