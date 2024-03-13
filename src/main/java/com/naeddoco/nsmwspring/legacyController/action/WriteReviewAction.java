package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;

import org.springframework.web.multipart.MultipartRequest;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.ReviewDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.ReviewDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WriteReviewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 구매내역-리뷰 작성 로직 
		 */
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		ReviewDTO rDTO = new ReviewDTO();
		ReviewDAO rDAO = new ReviewDAO();

		// 이미지 테스트
		
		//업로드된 파일이 저장될 디렉토리 위치(사용하는 워크스페이스의 img폴더 주소)
		String directory = ".\\src\\main\\webapp\\img";
        String currentDirectory = System.getProperty("user.dir");

        // 출력
        System.out.println("현재 작업 디렉토리: " + currentDirectory);

		
		// 파일크기 제한 10MB(단위 byte)
		int sizeLimit = 10 * 1024 * 1024; 

		// MultipartRequest(파일 업로드를 처리하는데 사용되는 클래스)
		// 양식 (HttpServletRequest 변수, 업로드된 파일이 저장될 디렉토리 위치, 파일크기 제한 byte크기, encoding설정, 동일한 파일명에 대한 처리 방식)
		MultipartRequest multi = new MultipartRequest(request, directory, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());
	
        // 업로드된 파일의 이름을 변수에 저장
        String imageFileName = multi.getFilesystemName("imageFile");
        
		// 이미지 테스트

		// 세션의 MID 가져오기
		String MID = (String) session.getAttribute("member");

		// V_buyinfo.jsp -> BID/score/contents 가져오기
		String strBID = (String) multi.getParameter("BID");
		String strScore = (String) multi.getParameter("score");
		String contents = (String) multi.getParameter("contents");
		// int로 변환
		int BID = Integer.parseInt(strBID);
		int score = Integer.parseInt(strScore);

		// MID,BID,score,contents로 reviewInsert
		rDTO.setMID(MID);
		rDTO.setBID(BID);
		rDTO.setScore(score);
		rDTO.setContents(contents);
		// 이미지 이름을 DTO에 저장
		rDTO.setImageName(imageFileName);
		rDTO.setSearchCondition("리뷰작성");
//		System.out.println("[log] /WriteReviewAction rDTO [" + rDTO + "]");
		boolean insertReviewResult = rDAO.insert(rDTO);

		// 리뷰작성유무확인
		// BID로 hasReview = 1로 업데이트
//       24.01.31 [준현] 모델 쪽 트리거 사용으로 인한 update 불필요
//      bDTO.setBID(BID);
//      bDTO.setSearchCondition("리뷰유무");
//      boolean updateHasReviewResult = bDAO.update(bDTO);

		// 리뷰작성성공시 hasReview도 함께 업데이트하여 페이지이동
//      if(insertReviewResult && updateHasReviewResult) {
		if (insertReviewResult) {
			// 성공시 리뷰목록으로 이동
//			System.out.println("[log] reviewInsert 성공! ");
			forward.setPath("reviewInfoPage.do");
			forward.setRedirect(false);
		} else {
			// 실패시 에러페이지
//			System.out.println("[log] reviewInsert 실패 ");
			forward.setPath("error.do");
			forward.setRedirect(true);
		}
		return forward;
	}

}