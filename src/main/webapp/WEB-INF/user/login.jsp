<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

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

<!-- 카카오 SDK 로드 -->
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.6.0/kakao.min.js" integrity="sha384-6MFdIr0zOira1CHQkedUqJVql0YtcZA1P0nbPrQYJXVJZUkTk/oX4U9GhUIs3/z8" crossorigin="anonymous"></script>

<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- 파비콘 -->
<custom:favicon/>
</head>
<body>

	<!-- 로그인 실패 모달 -->
	<c:if test="${loginResult != null}">
		<c:if test="${loginResult == false}">
			<script type="text/javascript">
				window.onload = function() {
					loginFail();
				};
				function loginFail() {
					Swal.fire({
						icon : 'error',
						title : '로그인 실패',
						text : '로그인이 실패하였습니다.',
					})
				}
			</script>
		</c:if>
	</c:if>
	<!-- 로그인 실패 모달 -->

	<!-- Spinner Start -->
	<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<!-- Spinner End -->

	<!-- Navbar start -->
	<div class="container-fluid fixed-top">
		<custom:commonHeader/>
		<custom:commonHeaderWithLogo/>
	</div>
	<!-- Navbar End -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">로그인</h1>
	</div>
	<!-- Single Page Header End -->


	<div class="container-fluid py-5">
		<div class="container py-5 text-center">
			<div class="row justify-content-center">
				<div class="col-lg-6">
					<!-- 로그인 폼 -->
					<form action="login.do" method="POST">
						<input class="form-control p-3 border-secondary my-3" type="text" name="MID" placeholder="아이디" required> 
						<input class="form-control p-3 border-secondary my-3" type="password" name="mPassword" placeholder="비밀번호" required>
						<input class="btn border-secondary text-primary rounded-pill py-3 px-5 my-3" type="submit" value="로그인"> 
						<a class="btn border-secondary text-primary rounded-pill py-3 px-5 my-3" href="termsPage.do">회원가입</a>
					</form>
					<!-- 로그인 폼 -->
				</div>
			</div>
			<hr>
			<!-- 카카오 로그인 -->
			<a id="kakao-login-btn"></a>
			<!-- 카카오 로그인 -->
		</div>
	</div>


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
	
	<!-- 카카오 로그인 -->
	<script>
	//카카오 초기화
	Kakao.init('8a69ee438b3b0270acfb88808676567f'); // 사용하려는 앱의 JavaScript 키 입력
	console.log(Kakao.isInitialized()); // 초기화 판단여부
	
	Kakao.Auth.createLoginButton({
		container: '#kakao-login-btn',
		success: function() {
			Kakao.API.request({
				url: '/v2/user/me',
				success: function(result) {
					location.replace("kakaoLogin.do?kakaoId=" + result.id);
				},
	            fail: function(error) {
					Swal.fire({
						icon : 'error',
						title : '로그인 실패',
						text : '로그인이 실패하였습니다.',
					})
				},
			})
		},
		fail: function(err) {
			Swal.fire({
				icon : 'error',
				title : '로그인 실패',
				text : '로그인이 실패하였습니다.',
			})
		},
	})
	</script>
	<!-- 카카오 로그인 -->
	

</body>
</html>