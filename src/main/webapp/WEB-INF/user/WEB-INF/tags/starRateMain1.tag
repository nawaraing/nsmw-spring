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
    function setRating1(rating, index) { 
        var selectedStar = document.getElementById(rating + '-stars1-' + index);
        if (selectedStar) {
            selectedStar.checked = true;
        }
    }
    setRating1('${score}', '${index}');
</script>