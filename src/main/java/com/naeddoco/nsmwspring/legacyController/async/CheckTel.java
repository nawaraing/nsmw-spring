//package com.naeddoco.nsmwspring.legacyController.async;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Random;
//
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mysql.cj.protocol.Message;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import net.nurigo.sdk.NurigoApp;
//import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
//import net.nurigo.sdk.message.service.DefaultMessageService;
//
//@WebServlet("/checkTel")
//public class CheckTel extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    public CheckTel() {
//        super();
//    }
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		@RestController
//		public class ExampleController {
//			
//			final DefaultMessageService messageService;
//
//		    public ExampleController() {
//		        this.messageService = NurigoApp.INSTANCE.initialize("INSERT_API_KEY", "INSERT_API_SECRET_KEY", "https://api.coolsms.co.kr");
//		    }
//			
//		}
//		
//		//받아온 연락처 010,XXXX,XXXX -> 010XXXXXXXX 형식으로 합치기
//		String phoneNumber1 = (String)request.getParameter("phoneNum1");
//		String phoneNumber2 = (String)request.getParameter("phoneNum2");
//		String phoneNumber3 = (String)request.getParameter("phoneNum3");
//		String combinedPhoneNumber = phoneNumber1 + phoneNumber2 + phoneNumber3;
//		
//		//5자리의 인증번호 랜덤으로 생성
//		Random random  = new Random();
//        String authNum = "";
//        //5회 반복
//        for(int i = 0; i < 5; i++) {
//        	// 0부터 9까지 랜덤으로 숫자생성
//            String ranNum = Integer.toString(random.nextInt(10));
//            // 랜덤으로 나온 숫자를 누적 => 5자리
//            authNum += ranNum;
//        }
//        
//        //coolsms API를 사용하여 sms 발송
//		DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSUDWN0MRTNVKOZ", "VFCCDXIBPRJJIOCEMFZAUIAUEX7YZPUJ", "https://api.coolsms.co.kr");
//		Message message = new Message();
//		message.setFrom("01033157366");
//		message.setTo(combinedPhoneNumber);
//		message.setText("내또코 영양제 쇼핑몰 인증번호 [" + authNum + "]을 입력해주세요.(*´ω`*)~♡'");
//
//		try {
//		  messageService.send(message);
//		} catch (NurigoMessageNotReceivedException exception) {
//		  // 발송에 실패한 메시지 목록을 확인 가능!
//		  System.out.println(exception.getFailedMessageList());
//		  System.out.println(exception.getMessage());
//		} catch (Exception exception) {
//		  System.out.println(exception.getMessage());
//		}
//		
//		//생성된 인증번호 전달
//		PrintWriter out = response.getWriter();
//		out.print(authNum);	
//	}
//
//}
