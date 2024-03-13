<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="score" %>
<%@ attribute name="index" %>

<link rel="stylesheet" type="text/css" href="css/starRating+.css" />

<div class="star-rating mx-auto">
	<input type="radio" id="5-stars1-${index}" name="stars1-${index}" value="5"/> 
	<label class="star">★</label> 
	<input type="radio" id="4-stars1-${index}" name="stars1-${index}" value="4"/> 
	<label class="star">★</label> 
	<input type="radio" id="3-stars1-${index}" name="stars1-${index}" value="3"/> 
	<label class="star">★</label> 
	<input type="radio" id="2-stars1-${index}" name="stars1-${index}" value="2"/> 
	<label class="star">★</label> 
	<input type="radio" id="1-stars1-${index}" name="stars1-${index}" value="1"/> 
	<label class="star">★</label>
</div>

<script>
	console.log("[로그] 별점1:"+${score});
	console.log("[로그] index:"+${index});
    function setRating1(rating, index) { 
    	console.log("[로그] 별점1: 스크립트 진입");
        var selectedStar = document.getElementById(rating + '-stars1-' + index);
        console.log("id : " + rating + '-stars1-' + index)

        console.log("[로그] 별점1: 엘레먼트 습득");
        if (selectedStar) {
        	console.log("[로그] 별점1: if문 진입");
            selectedStar.checked = true;
            console.log("[로그] 별점1: 엘레먼트 체크 true화");
        }
    }
    setRating1('${score}', '${index}');
</script>