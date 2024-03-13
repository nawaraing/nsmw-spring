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
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">

<!-- 파비콘 -->
<custom:favicon/>
</head>

<body>

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>

	<!-- 휴대폰 인증 요청 -->
	<script type="text/javascript">
		var telResult;
		function checkTel() {
			var phoneNum1 = $("#phoneNum1").val();
			var phoneNum2 = $("#phoneNum2").val();
			var phoneNum3 = $("#phoneNum3").val();
			$.ajax({
				type : "POST",
				url : "checkTel",
				data : {
					'phoneNum1' : phoneNum1,
					'phoneNum2' : phoneNum2,
					'phoneNum3' : phoneNum3
				},
				success : function(data) {
					telResult = data;
					var authNum = document.getElementById("authNum");
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
			var authNum = $("#authNum").val();
			if (telResult === authNum) {
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


	<!-- Navbar start -->
	<div class="container-fluid fixed-top">
		<custom:commonHeader/>
		<div class="container px-0">
			<nav class="navbar navbar-light bg-white navbar-expand-xl">
				<!-- 로고 버튼 -->
				<a href="mainPage.do" class="navbar-brand">
  					<img src="img/favicon.png" width="70" alt="대체 텍스트">
				</a>
				<a href="mainPage.do" class="navbar-brand">
  					<img src="img/logo.png" width="250" alt="대체 텍스트">
				</a>
				<!-- 로고 버튼 -->
				<button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
					<span class="fa fa-bars text-primary"></span>
				</button>
				<div class="collapse navbar-collapse bg-white" id="navbarCollapse">
					<div class="navbar-nav mx-auto">
						<a class="btn text-primary mb-0 mt-1" class="btn text-primary mb-0 mt-1" href="checkUserPasswordPage.do?where=modifyUserInfo" class="nav-item nav-link">개인정보수정</a>
						<a href="checkUserPasswordPage.do?where=modifyUserPassword" class="nav-item nav-link">비밀번호변경</a> 
						<a href="buyInfoPage.do" class="nav-item nav-link">구매내역</a> 
						<a href="reviewInfoPage.do" class="nav-item nav-link">리뷰내역</a> 
						<a href="couponInfoPage.do" class="nav-item nav-link">쿠폰관리</a>
					</div>
					<div class="d-flex m-3 me-0">
						<a class="btn border border-secondary text-primary rounded-pill position-relative my-auto me-4" href="logout.do">로그아웃</a>
						<button class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4" data-bs-toggle="modal" data-bs-target="#searchModal">
							<i class="fas fa-search text-primary"></i>
						</button>
						<a href="cartPage.do" class="position-relative me-4 my-auto"> <i class="fa fa-shopping-bag fa-2x"></i>
						</a> <a href="mypage.do" class="my-auto"> <i class="fas fa-user fa-2x"></i>
						
						</a>
					</div>
				</div>
			</nav>
		</div>
	</div>
	<!-- Navbar End -->


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
					<form action="modifyUserInfo.do" method="POST" name="joinForm" id="joinForm" onsubmit="return checkField()">
						<div class="row g-4">
							<div class="col-lg-12">
								<input class="form-control p-3  border-secondary" type="text" value="${memberInfo.mName}" name="mName" placeholder="이름" required>
							</div>
							<input class="form-control p-3 border-secondary" type="hidden" id="originPhoneNum" value="${memberInfo.phoneNumber}" readonly required>
							<div class="col-lg-2">
								<input class="form-control p-3 border-secondary" type="text" id="phoneNum1" name="phoneNum1" value="010" readonly required>
							</div>
							<div class="col-lg-3">
								<input class="form-control p-3 border-secondary" type="text" id="phoneNum2" name="phoneNum2" placeholder="0000" required>
							</div>
							<div class="col-lg-3">
								<input class="form-control p-3 border-secondary" type="text" id="phoneNum3" name="phoneNum3" placeholder="0000" required>
							</div>
							<div class="col-lg-4">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="checkTel()">인증번호 발송</button>
							</div>
							<div class="col-lg-8">
								<input class="form-control p-3 border-secondary" type="text" name="authNum" placeholder="인증번호">
							</div>
							<div class="col-lg-4">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="checkAuthNum()">인증번호 확인</button>
							</div>
							<input class="form-control p-3 border-secondary" type="hidden" id="email" placeholder="이메일 아이디"  value="${memberInfo.email}" required>
							<div class="col-lg-4">
								<input class="form-control p-3 border-secondary" type="text" id="email1" name="email1" placeholder="이메일 아이디" required>
							</div>
							<div class="col-lg-1">
								<P class="mt-3">@</P>
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3 border-secondary" type="text" id="email2" name="email2" placeholder="이메일 주소" required>
							</div>
							<div class="col-lg-8">
								<input class="form-control p-3 border-secondary" type="text" value="${memberInfo.mPostCode}" name="zipcode" placeholder="우편번호" readonly required>
							</div>
							<div class="col-lg-4">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="">우편번호 찾기</button>
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3 border-secondary " type="text" value="${memberInfo.mAddress}" name="address1" placeholder="도로명 주소" readonly required>
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3 border-secondary" type="text" value="${memberInfo.mDetailedAddress}" name="address2" placeholder="지번 주소" readonly required>
							</div>
							<div class="col-lg-6">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="checkRequirement()">수정</button>
							</div>
							<div class="col-lg-6">
								<a class="btn border border-secondary text-primary rounded-pill px-5 py-3" href="mypage.do">취소</a>
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
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/lightbox/js/lightbox.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="js/main.js"></script>

	<script>
		function checkField() {

			if (!document.joinForm.MID.value) {

				alert("비밀번호를 입력하지 않았습니다.");

				document.joinForm.focus();

				return false;

			}

		}
	</script>
	
		<!-- 필수 항목 누락 검사 -->
	<script>
		function checkRequirement() {
			var joinForm = document.getElementById("joinForm");
			console.log('phoneNum1 : ' + phoneNum1);
			console.log('phoneNum2 : ' + phoneNum2);
			console.log('#phoneNum1 : ' + document.getElementById("phoneNum2").value);
			console.log('#phoneNum2 : ' + document.getElementById("phoneNum3").value);
			if (phoneNum1 == document.getElementById("phoneNum2").value && phoneNum2 == document.getElementById("phoneNum3").value) {
				joinForm.submit();
			} else if ((phoneNum1 != document.getElementById("phoneNum2").value || phoneNum2 != document.getElementById("phoneNum3").value) && authNumResult == true) {
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
	

	<!-- phoneNumber 파싱 -->
	<script type="text/javascript">
	    var originPhoneNum = $("#originPhoneNum").val();
	    console.log('originPhoneNum : ' + originPhoneNum);
	    var phoneNums = originPhoneNum.split('-');
	    console.log('phoneNums : ' + phoneNums);
	    var phoneNum0 = phoneNums[0];
	    var phoneNum1 = phoneNums[1];
	    var phoneNum2 = phoneNums[2];
	    document.getElementById("phoneNum1").value = phoneNum0;
	    document.getElementById("phoneNum2").value = phoneNum1;
	    document.getElementById("phoneNum3").value = phoneNum2;
	</script>
	
	<!-- email 파싱 -->
	<script type="text/javascript">
	    var originEmail = $("#email").val();
	    console.log('originEmail : ' + originEmail);
	    var emails = originEmail.split('@');
	    console.log('emails : ' + emails);
	    document.getElementById("email1").value = emails[0];
	    document.getElementById("email2").value = emails[1];
	</script>


</body>
</html>