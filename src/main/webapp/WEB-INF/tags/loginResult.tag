<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="logoutResult" %>

<c:if test="${logoutResult eq 'true'}">
	<script type="text/javascript">
		
		function logoutSuccess() {
			Swal.fire({
				icon : 'success',
				title : '로그아웃 성공',
				text : '로그아웃이 처리되었습니다.',
			})
		}
		logoutSuccess();
		
	</script>
</c:if>
