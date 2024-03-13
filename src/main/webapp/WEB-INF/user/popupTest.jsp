<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
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
		<a href="productAllPage.do" target="_blank"><img src="img/popup.jpg" alt="팝업 이미지"></a>
	</div>

	<div class="btn">
		<button onclick="closePopupAndSetCookie()">하루동안 안보기</button>
	</div>

		<script>
			function closePopupAndSetCookie() {
				var cookieName = "popupShown";
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
