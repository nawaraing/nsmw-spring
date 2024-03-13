package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;
import com.naeddoco.nsmwspring.legacyModel.dao.CouponDAO;
import com.naeddoco.nsmwspring.legacyModel.dao.MemberDAO;
import com.naeddoco.nsmwspring.legacyModel.dto.CouponDTO;
import com.naeddoco.nsmwspring.legacyModel.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ActionForward forward = new ActionForward();

		String MID = request.getParameter("MID");
		String mName = request.getParameter("mName");

		// 비밀번호 체크하는 로직 -> View 담당
		String mPassword = request.getParameter("mPassword1");
		//		String mPassword2 = request.getParameter("mPassword2");

		// dob format: yyyy-MM-dd
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		if (year.length() < 4) {
			while (year.length() < 4) {
				year += "0" + year;
			}
		} else if (year.length() > 4) {
			year.substring(0, 4);
		}
		if (month.length() == 1) {
			month += "0" + month;
		}
		if (day.length() == 1) {
			day += "0" + day;
		}

		String dateString = year + "-" + month + "-" + day;
		java.sql.Date sqlDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date utilDate = sdf.parse(dateString);

			sqlDate = new java.sql.Date(utilDate.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		String gender = request.getParameter("gender");
		// String 조합
		String phoneNumber = request.getParameter("phoneNum1") + "-" + request.getParameter("phoneNum2") + "-" + request.getParameter("phoneNum3");
		String email = request.getParameter("email1") + "@" + request.getParameter("email2");

		String zipNo = request.getParameter("zipNo");

		String roadAddrPart1 = request.getParameter("roadAddrPart1");
		String addrDetail = request.getParameter("addrDetail");
		//		String addr = zipNo + "; " + roadAddrPart1 + "; " + addrDetail;

		String healths = "";
		String health;
		health = request.getParameter("skel");
		if (health != null) {
			healths += health + ";";
		}
		health = request.getParameter("liver");
		if (health != null) {
			healths += health + ";";
		}
		health = request.getParameter("eye");
		if (health != null) {
			healths += health + ";";
		}
		health = request.getParameter("energy");
		if (health != null) {
			healths += health + ";";
		}
		health = request.getParameter("immune");
		if (health != null) {
			healths += health + ";";
		}
		health = request.getParameter("brain");
		if (health != null) {
			healths += health + ";";
		}
		health = request.getParameter("skin");
		if (health != null) {
			healths += health + ";";
		}
		health = request.getParameter("digest");
		if (health != null) {
			healths += health + ";";
		}
		
		String kakaoId = request.getParameter("kakaoId");


		MemberDTO mDTO = new MemberDTO();
		MemberDAO mDAO = new MemberDAO();

		mDTO.setSearchCondition("회원가입");
		mDTO.setMID(MID);
		mDTO.setmName(mName);
		mDTO.setmPassword(mPassword);
		mDTO.setDob(sqlDate);
		mDTO.setGender(gender);
		mDTO.setPhoneNumber(phoneNumber);
		mDTO.setEmail(email);
		//		System.out.println("[JoinAction] " + zipNo);
		mDTO.setmPostCode(Integer.parseInt(zipNo));
		mDTO.setmAddress(roadAddrPart1);
		mDTO.setmDetailedAddress(addrDetail);
		//		System.out.println("JoinAction: health: " + healths);
		mDTO.setHealth(healths);
		
		if (kakaoId == null) {
			mDTO.setLoginType("내또코필즈");
			mDTO.setKakaoId(null);
		} else {
			mDTO.setLoginType("카카오");
			mDTO.setKakaoId(kakaoId);
		}

		if(mDAO.insert(mDTO)) {
			// 신규 가입자 쿠폰 지급
			CouponDTO cpDTO = new CouponDTO();
			CouponDAO cpDAO = new CouponDAO();
			
			cpDTO.setSearchCondition("쿠폰추가");
			cpDTO.setMID(MID);
			cpDTO.setCpName("신규 가입자 쿠폰");
			
			// 간 50% 쿠폰
			cpDTO.setDiscount(50);
			cpDTO.setCategory("간");
			cpDAO.insert(cpDTO);
			
			// 눈 30% 쿠폰
			cpDTO.setDiscount(30);
			cpDTO.setCategory("눈");
			cpDAO.insert(cpDTO);
			
			forward.setPath("mainPage.do");
			forward.setRedirect(false);
		}else {
			return null;
		}
		return forward;
	}

}
