<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <script>
        $(document).ready(function() {
            // Controller에서 전달된 값 사용
            var valueFromModel = "${pageValue}";
            
            // 해당 값과 일치하는 링크에 CSS를 적용
            $('a.nav-link').each(function() {
                if ($(this).text().trim() === valueFromModel.trim()) {
                    $(this).css('color', 'green');
                }
            });
        });
    </script>


<div class="container px-0">
	<nav class="navbar navbar-light bg-white navbar-expand-xl">

		<!-- 로고 버튼 -->
		<a href="/" class="navbar-brand"> <img
			src="/resources/commonImages/favicon.png" width="70" alt="대체 텍스트">
		</a> <a href="/" class="navbar-brand"> <img
			src="/resources/commonImages/logo.png" width="250" alt="대체 텍스트">
		</a>
		<!-- 로고 버튼 -->

		<button class="navbar-toggler py-2 px-3" type="button"
			data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
			<span class="fa fa-bars text-primary"></span>
		</button>

		<div class="collapse navbar-collapse bg-white" id="navbarCollapse">
			<div class="navbar-nav mx-auto">
				<a href="/user/checkUserPasswordPage?where=modifyUserInfo" class="nav-item nav-link">개인정보수정</a> 
				<a href="/user/checkUserPasswordPage?where=modifyUserPassword" class="nav-item nav-link">비밀번호변경</a> 
				<a href="/user/buyInfoPage" class="nav-item nav-link">구매내역</a>
				<a href="/subscriptionDetail" class="nav-item nav-link">구독내역</a>
				<a href="/user/reviewInfo" class="nav-item nav-link">리뷰내역</a>
				<a href="/user/couponInfo" class="nav-item nav-link">쿠폰관리</a>
			</div>
		</div>


			<div class="d-flex m-3 me-0">


				<!-- 로그아웃 버튼 -->
				<c:if test="${not empty memberID}">
					<a
						class="btn border border-secondary text-primary rounded-pill position-relative my-auto me-4"
						href="/logout">로그아웃</a>
				</c:if>
				<!-- 로그아웃 버튼 -->


				<!-- 장바구니 버튼 -->
				<c:if test="${not empty memberID}">
					<a href="/cart" class="position-relative me-4 my-auto"> <i
						class="fa fa-shopping-bag fa-2x"></i>
					</a>
				</c:if>
				<c:if test="${empty memberID}">
					<a href="/login" class="position-relative me-4 my-auto"> <i
						class="fa fa-shopping-bag fa-2x"></i>
					</a>
				</c:if>
				<!-- 장바구니 버튼 -->


				<!-- 프로필 버튼 -->
				<c:if test="${not empty memberID}">
					<a href="/user/myPage" class="my-auto"> <i class="fas fa-user fa-2x"></i>
					</a>
				</c:if>
				<!-- 프로필 버튼 -->


				<!-- 로그인 버튼 -->
				<c:if test="${empty memberID}">
					<a
						class="btn border border-secondary text-primary rounded-pill position-relative my-auto me-4"
						href="/login">로그인</a>
				</c:if>
				<!-- 로그인 버튼 -->

			</div>

	</nav>
</div>

	<!-- JavaScript Libraries -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="/resources/user/lib/easing/easing.min.js"></script>
	<script src="/resources/user/lib/waypoints/waypoints.min.js"></script>
	<script src="/resources/user/lib/lightbox/js/lightbox.min.js"></script>
	<script src="/resources/user/lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="/resources/user/js/main.js"></script>