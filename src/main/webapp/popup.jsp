<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>팝업 테스트</title>
<style>
/* 팝업 창 스타일 지정 */
.popup {
	width: 400px;
	height: 600px;
	background-color: white;
	border: 1px solid #ccc;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
	text-align: center;
	padding: 20px;
}
.popup_img {
	width: 400px;
	height: 600px;
	background-color: white;
	border: 1px solid #ccc;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
	text-align: center;
}

.btn {
	position: fixed;
	bottom: 0;
	right: 0;
}

body {
	overflow: hidden;
}
</style>
</head>
<body>

	<!-- 팝업 내용을 나타내는 div 요소 추가 -->
	<div class="popup">
		<a href="/couponDownload/give?couponID=${param.couponID}" target="_blank"><img src="${param.couponImagePath}" class="popup_img"></a>
	</div>

	<div class="btn">
		<button onclick="closePopupAndSetCookie('${param.couponImagePath}')">하루동안 안보기</button>
	</div>

		<script>
			function closePopupAndSetCookie(couponImageName) {
				var cookieName = couponImageName;
				var cookieValue = "false";
				var expires = new Date();
				expires.setDate(expires.getDate() + 1); // 하루 뒤에 만료

				document.cookie = cookieName + "=" + cookieValue + "; expires="
						+ expires.toUTCString() + "; path=/";

				// 팝업 창 닫기
				window.close();
			}
		</script>

	

</body>
</html>
