package com.naeddoco.nsmwspring.controller.login;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.memberCategoryModel.MemberCategoryDTO;
import com.naeddoco.nsmwspring.model.memberCategoryModel.MemberCategoryService;
import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;
import com.naeddoco.nsmwspring.model.shippingAddressModel.ShippingAddressDTO;
import com.naeddoco.nsmwspring.model.shippingAddressModel.ShippingAddressService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Slf4j
@Controller
public class JoinController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberCategoryService memberCategoryService;
	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@RequestMapping(value = "/joinPage", method = RequestMethod.GET)
	public String joinPage() {
		
		System.out.println("[log] JoinController 회원가입 페이지로 이동");
		log.debug("JoinController 회원가입 페이지로 이동");
		
		return "user/join";
		
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(MemberDTO memberDTO, MemberCategoryDTO memberCategoryDTO, ShippingAddressDTO shippingAddressDTO, HttpServletRequest request) {
		
		log.debug("JoinController 회원가입");

		System.out.println("[JOIN] ID : " + memberDTO.getMemberID());
		System.out.println("[JOIN] 이름 : " + memberDTO.getMemberName());
//		String MID = request.getParameter("MID");
//		String mName = request.getParameter("mName");

		// 비밀번호 체크하는 로직 -> View 담당
		System.out.println("[JOIN] PW : " + memberDTO.getMemberPassword());
//		String mPassword = request.getParameter("mPassword1");
		//		String mPassword2 = request.getParameter("mPassword2");

		// dob format: yyyy-MM-dd
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		
		// 생년월일 조합 시작
		if (year.length() < 4) {
			while (year.length() < 4) {
				year += "0";
			}
		} else if (year.length() > 4) {
			year.substring(0, 4);
		}
		
		if (month.length() == 1) {
			
			month += "0";
			
		} else if (month.length() > 2) {
			
			month.substring(0, 2);
			
		}
		
		if (day.length() == 1) {
			
			day += "0";
			
		} else if (day.length() > 2) {
			
			day.substring(0, 2);
			
		}

		String dob = year + "-" + month + "-" + day;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
		    java.util.Date utilDateOfBirth = sdf.parse(dob);
		    
		    java.sql.Date sqlDateOfBirth = new java.sql.Date(utilDateOfBirth.getTime());

		    memberDTO.setDayOfBirth(sqlDateOfBirth);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("[JOIN] 생년월일 : " + memberDTO.getDayOfBirth());
		// 생년월일 조합 후 저장
		
//		String email = email1+"@"+email2;
		String email = request.getParameter("email1") + "@" + request.getParameter("email2");
		memberDTO.setEmail(email);
		System.out.println("[JOIN] 이메일 : " + memberDTO.getEmail());
		
//		String phoneNumber = phoneNum1+ "-" + phoneNum2 + "-" + phoneNum3;
		String phoneNumber = request.getParameter("phoneNum1") + "-" + request.getParameter("phoneNum2") + "-" + request.getParameter("phoneNum3");
		memberDTO.setPhoneNumber(phoneNumber);
		System.out.println("[JOIN] 전화번호 : " + memberDTO.getPhoneNumber());
		
		System.out.println("[JOIN] 성별 : " + memberDTO.getGender());
//		String gender = request.getParameter("gender");
		
		// 회원 가입
		memberDTO.setSearchCondition("join");			
		boolean joinResult = memberService.insert(memberDTO);	
		
		// 회원가입 실패
		if(!joinResult) {
			
			return "redirect:/terms";
			
		}
		
		System.out.println("※※※※※[회원가입 성공]※※※※※");

		System.out.println("[JOIN_ADDRESS] 우편번호 : " + shippingAddressDTO.getShippingPostcode());
		System.out.println("[JOIN_ADDRESS] 도로주소 : " + shippingAddressDTO.getShippingAddress());
		System.out.println("[JOIN_ADDRESS] 상세주소 : " + shippingAddressDTO.getShippingDetailAddress());
//		String zipNo = request.getParameter("zipNo");
//		String roadAddrPart1 = request.getParameter("roadAddrPart1");
//		String addrDetail = request.getParameter("addrDetail");
		
		shippingAddressDTO.setSearchCondition("joinShippingAddress");
		boolean addressResult = shippingAddressService.insert(shippingAddressDTO);
		
		// 배송지 추가 오류
		if(!addressResult) {
			
			return "redirect:/main";
			
		}
		
		System.out.println("※※※※※[배송지 추가 성공]※※※※※");
				

		ArrayList<Integer> healthList = new ArrayList<>();

		if (request.getParameter("skel") != null) {
			healthList.add(1);
		}
		
		if (request.getParameter("liver") != null) {
			healthList.add(2);
		}
		
		if (request.getParameter("eye") != null) {
			healthList.add(3);
		}
		
		if (request.getParameter("energy") != null) {
			healthList.add(4);
		}
		
		if (request.getParameter("immune") != null) {
			healthList.add(5);
		}
		
		if (request.getParameter("brain") != null) {
			healthList.add(6);
		}
		
		if (request.getParameter("skin") != null) {
			healthList.add(7);
		}
		
		if (request.getParameter("digest") != null) {
			healthList.add(8);
		}
		
		String kakaoId = request.getParameter("kakaoId");

		String memberID = request.getParameter("memberID");
		
		memberCategoryDTO.setSearchCondition("joinMemberCategory");
		memberCategoryDTO.setMemberID(memberID);
		for(int i = 0; i < healthList.size(); i++) {
			memberCategoryDTO.setCategoryID(healthList.get(i));		
			memberCategoryService.insert(memberCategoryDTO);
			
			System.out.println("※※※※※[회원 건강상태 추가 " + (i+1) + "개 성공]※※※※※");
		}
	
		
		return "redirect:/";	
	}
	
	@RequestMapping(value = "/checkID", method = RequestMethod.POST)
	public @ResponseBody String checkID(MemberDTO memberDTO) {
		
		System.out.println("[log] 아이디 중복확인 : " + memberDTO.getMemberID());
		log.debug("[log] 아이디 중복확인");
		
		//V로부터 MID 가져오기
//		String MID = request.getParameter("MID");
//		MemberDTO mDTO = new MemberDTO();
//		MemberDAO mDAO = new MemberDAO();
		
		//DB에서 중복된 ID가 있는지 확인
		memberDTO.setSearchCondition("idDuplicationCheck");
		memberDTO = memberService.selectOne(memberDTO); 
		
		//V에서 모달에 띄워줄 정보 보내기
		//mDTO가 null이면 중복된 아이디가 없다는 의미
		if(memberDTO == null) {			
			//suc -> 성공
//			out.print("suc");			
			return "suc";
		}else {
			//fail -> 실패
//			out.print("fail");		
			return "fail";
		}
	}
	

	// SMS 인증 비동기
	@RequestMapping(value = "/checkTel", method = RequestMethod.POST)
	public @ResponseBody String checkID(@RequestParam("phoneNum1") String phoneNum1,
									@RequestParam("phoneNum2") String phoneNum2,
									@RequestParam("phoneNum3") String phoneNum3){
		
		System.out.println("[CheckTel] 인증번호 발송 진입");
	
		
		//받아온 연락처 010,XXXX,XXXX -> 010XXXXXXXX 형식으로 합치기
		String combinedPhoneNumber = phoneNum1 + phoneNum2 + phoneNum3;
		
		System.out.println("[CheckTel] 입력받은 전화번호 : " + combinedPhoneNumber);
		
		//5자리의 인증번호 랜덤으로 생성
		Random random  = new Random();
        String authNum = "";
        //5회 반복
        for(int i = 0; i < 5; i++) {
        	// 0부터 9까지 랜덤으로 숫자생성
            String ranNum = Integer.toString(random.nextInt(10));
            // 랜덤으로 나온 숫자를 누적 => 5자리
            authNum += ranNum;
        }
        
        System.out.println("[CheckTel] 생성된 인증번호 : " + authNum);
        
        //coolsms API를 사용하여 sms 발송
		DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSIRGLICTR0VZBW", "KPT4Q2B8FIEZWSXLR4PRD3NTXDUKEQMM", "https://api.coolsms.co.kr");
		Message message = new Message();
		message.setFrom("01093193710");
		message.setTo(combinedPhoneNumber);
		message.setText("내또코 영양제 쇼핑몰 인증번호 [" + authNum + "]을 입력해주세요.(*´ω`*)~♡'");

		try {
		  messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
		  // 발송에 실패한 메시지 목록을 확인 가능!
		  System.out.println(exception.getFailedMessageList());
		  System.out.println(exception.getMessage());
		} catch (Exception exception) {
		  System.out.println(exception.getMessage());
		}
		
		//생성된 인증번호 전달
//		PrintWriter out = response.getWriter();
//		out.print(authNum);	
		
		return authNum;
		
	}

}
