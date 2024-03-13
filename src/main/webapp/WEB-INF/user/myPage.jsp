<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>마이페이지</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

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

<!-- kakao -->
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>

<!-- 파비콘 -->
<custom:favicon/>
</head>
<body>

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>
	

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
						<a href="checkUserPasswordPage.do?where=modifyUserInfo" class="nav-item nav-link">개인정보수정</a>
						<a href="checkUserPasswordPage.do?where=modifyUserPassword" class="nav-item nav-link">비밀번호변경</a> 
						<a href="buyInfoPage.do" class="nav-item nav-link">구매내역</a> 
						<a href="reviewInfoPage.do" class="nav-item nav-link">리뷰내역</a> 
						<a href="couponInfoPage.do" class="nav-item nav-link">쿠폰관리</a>
					</div>
				<div class="d-flex m-3 me-0">
				
				<!-- 로그아웃 버튼-->
					<button class="btn border border-secondary text-primary rounded-pill position-relative my-auto me-4" onclick="unlinkApp()">로그아웃</button>
					<div id="result"></div>
					<script type="text/javascript">
					Kakao.init('8a69ee438b3b0270acfb88808676567f'); // 사용하려는 앱의 JavaScript 키 입력
					console.log(Kakao.isInitialized()); // 초기화 판단여부
						function unlinkApp() {
							Kakao.API.request({
								url: '/v1/user/unlink',
								success: function(res) {
									location.replace("logout.do");
									console.log('success');
								},
								fail: function(err) {
									location.replace("logout.do");
									console.log('fail');
								},
							})
						}
					</script>
				<!-- 로그아웃 버튼 -->
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
		<h1 class="text-center text-white display-6">마이페이지</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- 마이 폼 시작 -->
	<div class="container-fluid py-5">
		<div class="container py-5 text-left">
			<table class="table">
				<tbody>
					<tr>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">이름</p>
							</div>
						</td>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">${memberInfo.mName}</p>
							</div>
						</td>
					</tr>
					<tr>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">성별</p>
							</div>
						</td>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">${memberInfo.gender}</p>
							</div>
						</td>
					</tr>
					<tr>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">생년월일</p>
							</div>
						</td>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">${memberInfo.dob}</p>
							</div>
						</td>
					</tr>
					<tr>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">휴대폰번호</p>
							</div>
						</td>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">${memberInfo.phoneNumber}</p>
							</div>
						</td>
					</tr>
					<tr>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">이메일</p>
							</div>
						</td>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">${memberInfo.email}</p>
							</div>
						</td>
					</tr>
					<tr>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">건강상태</p>
							</div>
						</td>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">${memberInfo.health}</p>
							</div>
						</td>
					</tr>
					<tr>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">주소</p>
							</div>
						</td>
						<td scope="row">
							<div class="d-flex align-items-center">
								<p class="mb-3 mt-4">${memberInfo.mAddress}, ${memberInfo.mDetailedAddress}</p>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 마이 폼 끝 -->


	<!-- 풋터 시작 -->
	<custom:commonFooter/>
	<!-- 풋터 끝 -->
	

	<!-- 카피라이트 시작 -->
	<custom:commonCopyright/>
	<!-- 카피라이트 끝 -->


	<!-- Back to Top -->
	<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i class="fa fa-arrow-up"></i></a>


	<!-- JavaScript Libraries -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/lightbox/js/lightbox.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="js/main.js"></script>
	
</body>

</html>