<%@ tag language="java" pageEncoding="UTF-8"%>


<style>
    .div-banner {
        position: absolute;
        top: 50%;
        right: 3%;
        transform: translateY(-50%);
        z-index: 5;
        width: 150px;
    }

    .div-banner .banner-item img {
        max-width: 100%;
        height: auto;
    }
</style>

<script>

	window.addEventListener('scroll', function() {
		
		console.log("-----스크롤 이벤트 리스너-----");
		
		// div-banner의 클래스인 태그
	    var divBanner = document.querySelector('.div-banner');
		
		// 현재 페이지의 높이
	    var windowHeight = window.innerHeight;
		console.log("페이지의 높이 : " + windowHeight);
		
		// 스크롤 값을 저장(초기값 0) // window.scrollY를 지원하지 않는 구형 브라우저는 window.pageYOffset값 저장
	    var scrollY = window.scrollY || window.pageYOffset;
	    console.log("스크롤의 위치 : " + scrollY);
		
		// footer클래스 태그
	    var footer = document.querySelector('.footer');
		
		// footer클래스 태그의 상단 위치
	    var footerTop = footer.offsetTop;
	    console.log("footer의 상단 위치 : " + footerTop);
		
		// 배너의 높이
	    var bannerHeight = divBanner.offsetHeight;
	    console.log("배너의 높이 : " + bannerHeight);
	
	    // 배너 하단의 현재 위치 계산
	    var bannerBottomPosition = scrollY + windowHeight / 2 + bannerHeight / 2;
	    console.log("배너 하단의 위치 : " + bannerBottomPosition); 
	
	   if (bannerBottomPosition < footerTop) {
	        // 풋터에 닿기 전까지는 배너를 스크롤에 따라 움직임
	        divBanner.style.position = 'fixed';
	        divBanner.style.top = '50%';
	    } else {
	        // 풋터에 닿으면 배너의 위치 고정
	        divBanner.style.position = 'absolute';
	        divBanner.style.top = (footerTop - bannerHeight) + 'px'; // 배너 하단이 풋터 상단에 위치하도록 조정
	    }
	});

</script>


<%-- 	<div class="div-banner">
		<c:forEach var="bestProduct" items="${recommandProducts}" varStatus="status">  
			<c:if test="${status.index < 5}">
				<!--<div class="banner-item" onclick='location.href="/productDetail?productID=${bestProduct.productID}";'>-->       
				<div class="banner-item" onclick="window.open('https://github.com/NaeDdoCo', '_blank');">     
					<img src="${bestProduct.ancImagePath}" alt="BestSeller이미지">
				</div>
			</c:if>
		</c:forEach>
	</div> --%>
	
<div class="div-banner">
	<div class="banner-item" onclick="window.open('https://github.com/NaeDdoCo', '_blank');">     
		<img src="/resources/commonImages/banner.jpg" alt="배너이미지">
	</div>
</div>
