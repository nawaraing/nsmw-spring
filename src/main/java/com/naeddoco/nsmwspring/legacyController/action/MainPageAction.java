package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.BuyInfoDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.MemberDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.ProductDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.ReviewDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.BuyInfoDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.MemberDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.ProductDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.ReviewDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MainPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ActionForward forward = new ActionForward();

		ArrayList<ProductDTO> rcmDTOs = new ArrayList<ProductDTO>();
		ArrayList<ProductDTO> pDTOs = new ArrayList<ProductDTO>();
		ProductDTO pDTO = new ProductDTO();
		ProductDAO pDAO = new ProductDAO();

		// 메인페이지 하단 전체상품_8개
		pDTO.setSearchCondition("상품목록페이지");
		pDTO.setAncSelectMin(1);
		pDTO.setAncSelectMax(8);
		pDTOs = pDAO.selectAll(pDTO);
		
		// 각 상품의 평균 별점 설정
		for (ProductDTO productDTO : pDTOs) {
			int pid = productDTO.getPID();
			int avgRating = getAverageRating(pid);
			productDTO.setAncAvgRating(avgRating);
//			System.out.println("[log] PID : " + productDTO.getPID());
//			System.out.println("[log] pDTOs 평균별점 " + productDTO.getAncAvgRating());
		}

		// 메인페이지 상단 추천상품_8개
		pDTO.setSearchCondition("상품출력필터");
		rcmDTOs = pDAO.selectAll(pDTO);
		
		rcmDTOs = recommendProduct(request, rcmDTOs);
//		System.out.println("[log] rcmDTOs : "+ rcmDTOs);
		
		// 추천 상품이 0개면 판매량순 추천
		request.setAttribute("pDTOs", pDTOs);
		if (rcmDTOs == null) {
			request.setAttribute("rcmDTOs", pDTOs);
		} else {
			// 각 상품의 평균 별점 설정
			for (ProductDTO productDTO : rcmDTOs) {
				int pid = productDTO.getPID();
				int avgRating = getAverageRating(pid);
				productDTO.setAncAvgRating(avgRating);
//				System.out.println("[log] productDTO : " + productDTO);
//				System.out.println("[log] PID : " + productDTO.getPID());
//				System.out.println("[log] rcmDTOs 평균별점 " + productDTO.getAncAvgRating());
			}
			
			int i = 0;
			while (rcmDTOs.size() < 8) {
				rcmDTOs.add(pDTOs.get(i++));
			}
			request.setAttribute("rcmDTOs", rcmDTOs);
		}

		forward.setPath("main.jsp");
		forward.setRedirect(false);

//		System.out.println("ctrl rcmDTOs: " + rcmDTOs);
//		System.out.println("ctrl pDTOs: " + pDTOs);
		return forward;
	}

	/*
	 * 비로그인 -> 판매량순 추천
	 * 로그인 -> HEALTH 기반 추천
	 *  
	 * return
	 * 1. 추천 상품 목록 0이면, 추천 상품 없음
	 * 2. null이면, 에러
	 */
	private ArrayList<ProductDTO> recommendProduct(HttpServletRequest request, ArrayList<ProductDTO> allDTOs) {
		System.out.println("recommendProduct: allDTOs: " + allDTOs);

		ArrayList<ProductDTO> rcmDTOs = new ArrayList<ProductDTO>();
		String health = "";
		// 상품 추천 알고리즘
		// 로그인: HEALTH 기반 추천
		// 비로그인: 판매량순 추천
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("member");

		MemberDAO mDAO = new MemberDAO();
		MemberDTO mDTO = new MemberDTO();

		if (mid != null) { // 로그인 : HEALTH 기반 추천
//			System.out.println("recommendProduct: 로그인");
//			System.out.println("recommendProduct: mid: " + mid);
			
			mDTO.setSearchCondition("건강상태");
			mDTO.setMID(mid);
			mDTO = mDAO.selectOne(mDTO);
			
			if (mDTO != null) {
				health = mDTO.getHealth();
			} else {
//				System.out.println("recommendProduct: mDTO == null");
				return null;
			}
			if (health == null) {
				return null;
			}

			String[] healths = health.split(";");
			ArrayList<ProductDTO> tmpDTOs = new ArrayList<ProductDTO>();
			for (ProductDTO pDTO : allDTOs) {
				for (int i = 0; i < healths.length; i++) {
					health = healths[i];
					if (health.equals(pDTO.getCategory())) {
						tmpDTOs.add(pDTO);
					}
				}
			}

			// 8개 선정
			int cnt = 0;
			for (ProductDTO data : tmpDTOs) {
				rcmDTOs.add(data);
				cnt++;
				if (cnt == 8) {
					break;
				}
			}

		} else { // 비로그인 : 판매량순 추천
			System.out.println("recommendProduct: 비로그인");
			// allDTOs 판매량순 정렬

			Comparator<ProductDTO> comparator = new Comparator<ProductDTO>() {
				@Override
				public int compare(ProductDTO o1, ProductDTO o2) {
					BuyInfoDAO bDAO = new BuyInfoDAO();
					BuyInfoDTO bDTO = new BuyInfoDTO();
					int qty1;
					int qty2;

					bDTO.setSearchCondition("판매량");
					bDTO.setPID(o1.getPID());
					bDTO = bDAO.selectOne(bDTO);
					if (bDTO == null) {
						qty1 = 0;
						bDTO = new BuyInfoDTO();
					} else {
						qty1 = bDTO.getbQty();
					}

					bDTO.setSearchCondition("판매량");
					bDTO.setPID(o2.getPID());
					bDTO = bDAO.selectOne(bDTO);
					if (bDTO == null) {
						qty2 = 0;
					} else {
						qty2 = bDTO.getbQty();
					}

					return qty2 - qty1;
				}
			};

			// 정렬
			allDTOs.sort(comparator);

			// 8개 뽑기
			int cnt = 0;
			for (ProductDTO data : allDTOs) {
				rcmDTOs.add(data);
				cnt++;
				if (cnt == 8) {
					break;
				}
			}
		}
//		System.out.println(rcmDTOs);
//		for (ProductDTO data : rcmDTOs) {
//			retDTOs.add(data);
//		}
		return rcmDTOs;
	}
	
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
