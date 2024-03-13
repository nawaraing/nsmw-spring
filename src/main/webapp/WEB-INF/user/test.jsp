<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모델 테스트 페이지</title>
</head>
<body>

	<%-- Model 테스트 코드 --%>
	<h1>모델 테스트</h1>

	<a href="testSelectAll.do">selectAll</a><br>
	<a href="testSelectOne.do">selectOne</a><br>
	<a href="testInsert.do">insert</a><br>
	<a href="testUpdate.do">update</a><br>
	<a href="testDelete.do">delete</a><br>

	<hr>

	<%-- Controller 테스트 코드 --%>
	<form action="join.do" method="POST">
		<input type="hidden" name="MID" value="teemo" >
		<input type="hidden" name="mName" value="티모" >
		<input type="hidden" name="mPassword" value="1234" >
		<input type="hidden" name="mpwCheck" value="1234" >
		<input type="hidden" name="year" value="2000" >
		<input type="hidden" name="month" value="3" >
		<input type="hidden" name="day" value="1" >
		<input type="hidden" name="gender" value="남" >
		<input type="hidden" name="phoneNumber0" value="010" >
		<input type="hidden" name="phoneNumber1" value="1234" >
		<input type="hidden" name="phoneNumber2" value="5678" >
		<input type="hidden" name="email" value="nawaddaing@gmail.com" >
		<input type="hidden" name="address" value="인천시 계양구 임학안로 34-10" >
		<input type="hidden" name="health" value="눈" >
		
		<input type="submit" value="컨트롤러 테스트" >
	</form>


</body>
</html>