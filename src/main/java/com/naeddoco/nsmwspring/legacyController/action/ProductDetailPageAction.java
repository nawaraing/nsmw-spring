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

public class ProductDetailPageAction implements Action{

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
		/* 
		 * 상품상세조회페이지
		 */
      ActionForward forward = new ActionForward();
      ProductDAO pDAO = new ProductDAO();
      ProductDTO pDTO = new ProductDTO();
      ReviewDAO rDAO=new ReviewDAO();
      ReviewDTO rDTO=new ReviewDTO();
      
      //V로부터 넘겨받은 정보 : PID
      String strPID = request.getParameter("PID");
      //int로 형변환
      int PID = Integer.parseInt(strPID);
      
      //DB에서 상품상세정보 가져오기
      pDTO.setPID(Integer.parseInt(request.getParameter("PID")));
      pDTO.setSearchCondition("상품상세정보");
      pDTO = pDAO.selectOne(pDTO);
      
      //해당 PID의 평균 별점 가져오기
      int avgRating = getAverageRating(PID);
      pDTO.setAncAvgRating(avgRating);
//      System.out.println("[log] ProductDetailPageAction rDTO PID : " + pDTO.getPID());
//      System.out.println("[log] ProductDetailPageAction rDTO 평균별점 : " + pDTO.getAncAvgRating());
      
      //PID로 해당 상품의 리뷰내역 조회
      rDTO.setAncPID(PID);
      rDTO.setSearchCondition("상품리뷰");
      ArrayList<ReviewDTO> reviewList = rDAO.selectAll(rDTO);
//    System.out.println("[log]reviewList :"+reviewList);
      rDTO.setAncPID(PID);
       
      //조회된 상품 정보와 리뷰 내역을 V에 전달
      request.setAttribute("productDetail", pDTO);
      request.setAttribute("reviewList", reviewList);
        
      forward.setPath("productDetail.jsp");
      forward.setRedirect(false);
      
      return forward;
   }
   
   //평균별점 계산 메서드(소수점자리 절삭)
	private int getAverageRating(int PID) {
//		System.out.println("getAverageRating 메서드 진입");
	    ReviewDTO rDTO = new ReviewDTO();
	    ReviewDAO rDAO = new ReviewDAO();
	    
	    rDTO.setAncPID(PID);
	    rDTO.setSearchCondition("별점평균");
	    
	    rDTO = rDAO.selectOne(rDTO);
	    
	    if (rDTO != null) {
	        // 평균값이 있다면, int 평균 별점 값을 반환
	        return (int) rDTO.getAncAvgScore();
	    } else {
	        // 평균 별점이 없을 경우 기본값 0 반환
	        return 0;
	    }
	}
   

}