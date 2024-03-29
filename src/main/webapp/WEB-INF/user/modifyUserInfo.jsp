<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>개인정보수정</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

<!-- sweetalert2 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="/resources/user/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="/resources/user/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="/resources/user/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="/resources/user/css/style.css" rel="stylesheet">

<!-- 파비콘 -->
<custom:favicon/>
</head>

<body>

	<!-- 필수 항목 누락 검사 -->
	<script>
		function checkRequirement() {
			var joinForm = document.getElementById("joinForm");
			console.log('phoneNumber1 : ' + phoneNumber1);
			console.log('phoneNumber2 : ' + phoneNumber2);
			console.log('phoneNumber3 : ' + phoneNumber3);
			console.log('#phoneNumber1 : ' + document.getElementById("phoneNumber1").value);
			console.log('#phoneNumber2 : ' + document.getElementById("phoneNumber2").value);
			console.log('#phoneNumber3 : ' + document.getElementById("phoneNumber3").value);
			// 기존이랑 text칸 값이랑 같으면 걍 커밋
			if (phoneNumber1 == document.getElementById("phoneNumber1").value && phoneNumber2 == document.getElementById("phoneNumber2").value && phoneNumber3 == document.getElementById("phoneNumber3").value) {
				
				joinForm.submit();
				// 기존이랑 text칸 값이랑 다르면 인증했을 때 변수에 true가 들어가야 커밋
			} else if ((phoneNumber1 != document.getElementById("phoneNumber1").value || phoneNumber2 != document.getElementById("phoneNumber2").value) || phoneNumber3 != document.getElementById("phoneNumber3").value && authNumResult == true) {
				joinForm.submit();
			} else {
				Swal.fire({
					icon : 'error',
					title : '필수 항목 검사',
					text : '번호 인증을 진행해주세요.',
				})
			}
		}
	</script>
	<!-- 필수 항목 누락 검사 -->

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>

	<!-- 휴대폰 인증 요청 -->
	<script type="text/javascript">
		var telResult;
		function checkTel() {
			var phoneNumber1 = $("#phoneNumber1").val();
			var phoneNumber2 = $("#phoneNumber2").val();
			var phoneNumber3 = $("#phoneNumber3").val();
			$.ajax({
				type : "POST",
				url : "/checkTel",
				data : {
					'phoneNum1' : phoneNumber1,
					'phoneNum2' : phoneNumber2,
					'phoneNum3' : phoneNumber3
				},
				success : function(data) {
					telResult = data;
					console.log("인증번호 요청값 : " + telResult);
					authNum.readOnly = false
				}
			});
		}
	</script>
	<!-- 휴대폰 인증 요청 -->
	
	
	
	<!-- 휴대폰 인증번호 확인 -->
	<script type="text/javascript">
		var authNumResult = false;
		function checkAuthNum() {
			var authNum = document.getElementById("authNum").value;
			console.log("사용자가 입력한 인증번호 : " + authNum);
			if (telResult == authNum) {
				Swal.fire({
					icon : 'success',
					title : '휴대폰 인증',
					text : '인증이 완료되었습니다.',
				})
				authNumResult = true;
				var authNum = document.getElementById("authNum");
				authNum.readOnly = true
			} else {
				Swal.fire({
					icon : 'error',
					title : '휴대폰 인증',
					text : '인증이 실패하였습니다.',
				})
			}
		}
	</script>
	<!-- 휴대폰 인증번호 확인 -->

	<!-- Spinner Start -->
	<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<!-- Spinner End -->


	<!-- 로고가 포홤된 헤더 시작 -->
	<div class="container-fluid fixed-top">
			<custom:commonHeader />		
			<custom:myPageHeaderWithLogo />		
	</div>
	<!-- 로고가 포홤된 헤더 끝 -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">개인정보수정</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- 404 Start -->
	<div class="container-fluid py-5">
		<div class="container py-5 text-center">
			<div class="row justify-content-center">
				<div class="col-lg-6">
					<form action="/user/modifyUserInfo" method="POST" name="joinForm" id="joinForm" >
						<div class="row g-4">
							<div class="col-lg-12">
								<input class="form-control p-3  border-secondary" type="text" value="${memberInfo.memberName}" name="memberName" placeholder="이름" required>
							</div>
							<input class="form-control p-3 border-secondary" type="hidden" id="phoneNumber" name="phoneNumber" value="${memberInfo.phoneNumber}" readonly required>
							<div class="col-lg-2">
								<input class="form-control p-3 border-secondary" type="text" id="phoneNumber1" name="phoneNumber1" value="${phoneNumber1}" required>
							</div>
							<div class="col-lg-3">
								<input class="form-control p-3 border-secondary" type="text" id="phoneNumber2" name="phoneNumber2" value="${phoneNumber2}" required>
							</div>
							<div class="col-lg-3">
								<input class="form-control p-3 border-secondary" type="text" id="phoneNumber3" name="phoneNumber3" value="${phoneNumber3}" required>
							</div>
							<div class="col-lg-4">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="checkTel()">인증번호 발송</button>
							</div>
							<div class="col-lg-8">
								<input class="form-control p-3 border-secondary" type="text" name="authNum" id="authNum" placeholder="인증번호">
							</div>
							<div class="col-lg-4">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="checkAuthNum()">인증번호 확인</button>
							</div>
							<input class="form-control p-3 border-secondary" type="hidden" id="email" name="email" placeholder="이메일 아이디"  value="${memberInfo.email}" required>
							<div class="col-lg-4">
								<input class="form-control p-3 border-secondary" type="text" id="email1" name="email1" value="${email1}" required>
							</div>
							<div class="col-lg-1">
								<P class="mt-3">@</P>
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3 border-secondary" type="text" id="email2" name="email2" value="${email2}" required>
							</div>
							<input class="form-control p-3 border-secondary" type="hidden" id="ancShippingAddressID" name="ancShippingAddressID" value="${memberInfo.ancShippingAddressID}" readonly required>
							<div class="col-lg-8">
								<input class="form-control p-3 border-secondary" type="text" name="ancShippingPostCode" value="${memberInfo.ancShippingPostCode}"  readonly required>
							</div>
							<div class="col-lg-4">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="goPopup()">우편번호 찾기</button>
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3 border-secondary " type="text" name="ancShippingAddress" value="${memberInfo.ancShippingAddress}"  readonly required>
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3 border-secondary" type="text"  name="ancShippingAddressDetail" value="${memberInfo.ancShippingAddressDetail}" readonly required>
							</div>
							<div class="col-lg-6">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="checkRequirement()">수정</button>
							</div>
							<div class="col-lg-6">
								<a class="btn border border-secondary text-primary rounded-pill px-5 py-3" href="/user/myPage">취소</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 404 End -->


	<!-- 풋터 시작 -->
	<custom:commonFooter/>
	<!-- 풋터 끝 -->


	<!-- 카피라이트 시작 -->
	<custom:commonCopyright/>
	<!-- 카피라이트 끝 -->


	<!-- Back to Top -->
	<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"> <i class="fa fa-arrow-up"></i>
	</a>


	<!-- JavaScript Libraries -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="/resources/user/lib/easing/easing.min.js"></script>
	<script src="/resources/user/lib/waypoints/waypoints.min.js"></script>
	<script src="/resources/user/lib/lightbox/js/lightbox.min.js"></script>
	<script src="/resources/user/lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="/resources/user/js/main.js"></script>
	
		<!-- 주소 api 자바스크립트 -->
	<script language="javascript">
		// opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. 
		// (＂팝업 API 호출 소스"도 동일하게 적용시켜야 합니다.)
		//document.domain = "abc.go.kr";
		function goPopup() {

			//경로는 시스템에 맞게 수정하여 사용
			//호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://business.juso.go.kr/addrlink/addrLinkUrl.do)를
			//호출하게 됩니다.
			var pop = window.open("/jusoPopup.jsp", "pop",
					"width=570,height=420, scrollbars=yes, resizable=yes");

			//** 2017년 5월 모바일용 팝업 API 기능 추가제공 **/
			// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서
			// 실제 주소검색 URL(https://business.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
			// var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
		}

		function jusoCallBack(zipNo, roadAddrPart1, addrDetail) {

			// 2017년 2월 제공항목이 추가되었습니다. 원하시는 항목을 추가하여 사용하시면 됩니다.
			document.joinForm.ancShippingPostCode.value = zipNo;
			document.joinForm.ancShippingAddress.value = roadAddrPart1;
			document.joinForm.ancShippingAddressDetail.value = addrDetail;

		}
	</script>
	<!-- 주소 api 자바스크립트 -->

	<script>
/* 		function checkField() {

			if (!document.joinForm.MID.value) {

				alert("비밀번호를 입력하지 않았습니다.");

				document.joinForm.focus();

				return false;

			}

		} */
	</script>
	

	
	<!-- phoneNumber 파싱 -->
 	<script type="text/javascript">
/* 	    var phoneNumber = $("#phoneNumber").val();
	    console.log('phoneNumber : ' + phoneNumber);
	    var phoneNums = phoneNumber.split('-');
	    console.log('phoneNums : ' + phoneNums);
	    var phoneNumber1 = phoneNums[0];
	    var phoneNumber2 = phoneNums[1];
	    var phoneNumber3 = phoneNums[2];
		console.log('phoneNumber1 : ' + phoneNumber1);
		console.log('phoneNumber2 : ' + phoneNumber2);
		console.log('phoneNumber3 : ' + phoneNumber3);
	    document.getElementById("phoneNum1").value = phoneNum0;
	    document.getElementById("phoneNum2").value = phoneNum1;
	    document.getElementById("phoneNum3").value = phoneNum2; */
	</script>
	
	<!-- email 파싱 -->
 	<script type="text/javascript">
/* 	    var tempEmail = $("#email").val();
	    console.log('email : ' + email);
	    var email = tempEmail.split('@');
	    console.log('email : ' + email);
	    document.getElementById("email1").value = emails[0];
	    document.getElementById("email2").value = emails[1]; */
	</script>


</body>
</html>