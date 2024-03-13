<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>비밀번호확인</title>
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
	
		<!-- 로그인 실패 모달 -->
	<c:if test="${checkResult != null}">
		<c:if test="${checkResult == false}">
			<script type="text/javascript">
				window.onload = function() {
					checkFail();
				};
				function checkFail() {
					Swal.fire({
						icon : 'error',
						title : '비밀번호 검사',
						text : '잘못된 비밀번호 입니다.',
					})
				}
			</script>
		</c:if>
	</c:if>
	<!-- 로그인 실패 모달 -->
	
	<!-- 중복 버튼을 눌렀을 때 중복검사하는 ajax -->
	<script type="text/javascript">
		function checkPw() {
			// 사용자가 입력한 아이디 가져오기
			var mPassword = $("#mPassword").val();
			if (mPassword === "") {
				Swal.fire({
					icon : 'error',
					title : '비밀번호 검사',
					text : '비밀번호를 입력해주세요.',
				})
				return 0;
			}
			// AJAX 요청 보내기
			$.ajax({
				type : "POST", // 또는 "GET"
				url : "checkPw", // 서버에서 아이디 중복 확인을 처리할 PHP 파일 경로
				data : {
					'mPassword' : mPassword
				},
				success : function(data) {
					if (data === "diffPw") {
						Swal.fire({
							icon : 'error',
							title : '비밀번호 검사',
							text : '잘못된 비밀번호 입니다.',
						})
					} else {
						var joinForm = document.getElementById("joinForm");
						joinForm.submit();
					}
				}
			});
		}
	</script>
	<!-- 중복 버튼을 눌렀을 때 중복검사하는 ajax -->
		
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
		<h1 class="text-center text-white display-6">비밀번호확인</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- 404 Start -->
	<div class="container-fluid py-5">
		<div class="container py-5 text-center">
			<div class="row justify-content-center">
				<div class="col-lg-6">
					<form action="checkUserPassword.do" method="POST" name="joinForm" id="joinForm">
						<div class="row g-4">
							<div class="col-lg-12">
								<input class="form-control p-3  border-secondary" type="password" name="mPassword" id="mPassword" placeholder="비밀번호 확인" required>
							</div>
							<div class="col-lg-6">
								<input class="btn border-secondary text-primary rounded-pill py-3 px-5" type="button" value="확인" onclick="checkPw()">
							</div>
							<div class="col-lg-6">
								<a class="btn border border-secondary text-primary rounded-pill px-5 py-3" href="mypage.do">취소</a>
							</div>
							<c:set var="where" value="${param.where}" />
							<input type="hidden" id="where" name="where" value="${where}">
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
	<!-- JavaScript Libraries -->
	
	<!-- Template Javascript -->
	<script src="js/main.js"></script>
	<!-- Template Javascript -->
	
	
	<script>
		function checkField() {

			if ($("#password").val() == "") {
				Swal.fire({
					icon : 'error',
					title : '필수 항목 검사',
					text : '비밀번호를 입력해주세요. checkField()',
				})
			} else if (pwSame == false) {
				Swal.fire({
					icon : 'error',
					title : '필수 항목 검사',
					text : '비밀번호가 일치하지 않습니다. checkField()',
				})
			} else {
				var joinForm = document.getElementById("joinForm");
				joinForm.submit();
			}

		}
	</script>
	
</body>
</html>