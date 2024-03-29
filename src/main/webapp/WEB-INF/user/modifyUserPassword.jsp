<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>비밀번호변경</title>
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

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>
	
	<!-- 비밀번호 중복검사 -->
	<script type="text/javascript">
		var pwSame = false;
		function pwSameCheck() {
			if ($('#memberPassword').val() == $('#confirmPassword').val()) {
				pwSame = true
				Swal.fire({
					icon : 'success',
					title : '비밀번호 검사',
					text : '비밀번호가 일치합니다.',
				})
			} else {
				pwSame = false
				Swal.fire({
					icon : 'error',
					title : '비밀번호 검사',
					text : '비밀번호가 일치하지 않습니다.',
				})
			}
		}
	</script>
	<!-- 비밀번호 중복검사 -->


	<!-- 비밀번호 포맷 검사 -->
	<script>
		var pwFormat = false
		function pwFormatCheck() {

			var reg = new RegExp("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");

			if (!reg.test($('#memberPassword').val())) {
				pwFormat = false
				Swal.fire({
					icon : 'error',
					title : '비밀번호 검사',
					text : '영어 대소문자, 숫자, 특수문자를 포함해야합니다.',
				})
			} else {
				pwFormat = true
			}
		}
	</script>
	<!-- 비밀번호 포맷 검사 -->
	
	<!-- 필수 항목 누락 검사 -->
	<script>
		function checkRequirement() {
			if ($("#memberPassword").val() == "") {
				Swal.fire({
					icon : 'error',
					title : '필수 항목 검사',
					text : '비밀번호를 입력해주세요.',
				})
			} else if (pwSame == false) {
				Swal.fire({
					icon : 'error',
					title : '필수 항목 검사',
					text : '비밀번호가 일치하지 않습니다.',
				})
			} else if (pwFormat == false) {
				Swal.fire({
					icon : 'error',
					title : '필수 항목 검사',
					text : '영어 대소문자, 숫자, 특수문자를 포함해야합니다.',
				})
			} else {
				var memberPassword = $("#memberPassword").val();

				// AJAX 요청 보내기
				$.ajax({
					type : "POST", // 또는 "GET"
					url : "/checkPw", // 서버에서 아이디 중복 확인을 처리할 PHP 파일 경로
					data : {
						'memberPassword' : memberPassword
					},
					success : function(data) {
						if (data == "suc") {
							Swal.fire({
								icon : 'error',
								title : '비밀번호 검사',
								text : '기존 비밀번호와 동일합니다. 다른 비밀번호를 입력해주세요',
							})
						} else {
							var joinForm = document.getElementById("joinForm");
							joinForm.submit();
						}
					}
				});
			}
		}
	</script>
	<!-- 필수 항목 누락 검사 -->

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
		<h1 class="text-center text-white display-6">비밀번호변경</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- 404 Start -->
	<div class="container-fluid py-5">
		<div class="container py-5 text-center">
			<div class="row justify-content-center">
				<div class="col-lg-6">
					<form action="/user/modifyUserPassword" method="POST" name="joinForm" id="joinForm">
						<div class="row g-4">
							<div class="col-lg-12">
								<input class="form-control p-3 border-secondary " type="password" name="memberPassword" id="memberPassword" placeholder="비밀번호" maxlength="15" onblur="pwFormatCheck()">
							</div>
							<div class="col-lg-12">
								<input class="form-control p-3 border-secondary" type="password" id="confirmPassword" placeholder="재입력" maxlength="15" onblur="pwSameCheck()">
							</div>
							<div class="col-lg-6">
								<input class="btn border-secondary text-primary rounded-pill py-3 px-5" type="button" value="수정" onclick="checkRequirement()">
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

</body>
</html>