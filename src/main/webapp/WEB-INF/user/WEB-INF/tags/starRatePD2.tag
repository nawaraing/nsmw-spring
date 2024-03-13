<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="score" %>
<%@ attribute name="index" %>

<link rel="stylesheet" type="text/css" href="css/starRating+.css" />

<div class="star-rating mx-auto">
	<input type="radio" id="5-stars2-${index}" name="stars2-${index}" value="5"/> 
	<label class="star">★</label> 
	<input type="radio" id="4-stars2-${index}" name="stars2-${index}" value="4"/> 
	<label class="star">★</label> 
	<input type="radio" id="3-stars2-${index}" name="stars2-${index}" value="3"/> 
	<label class="star">★</label> 
	<input type="radio" id="2-stars2-${index}" name="stars2-${index}" value="2"/> 
	<label class="star">★</label> 
	<input type="radio" id="1-stars2-${index}" name="stars2-${index}" value="1"/> 
	<label class="star">★</label> 
</div>

<script>
	console.log("[로그] 별점2:"+${score});
    function setRating2(rating, index) { 
        var selectedStar = document.getElementById(rating + '-stars2-' + index);
        console.log("id : " + rating + '-stars2-' + index)
        if (selectedStar) {
            selectedStar.checked = true;
        }
    }
    setRating2('${score}', '${index}');
</script>