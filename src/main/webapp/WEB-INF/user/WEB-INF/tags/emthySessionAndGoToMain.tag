<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${empty sessionScope.member}">
		<c:redirect url="mainPage.do" />
	</c:when>
</c:choose>
