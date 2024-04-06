<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="rowNum" %>

<!-- Carousel -->
<div id="carousel-${rowNum}" class="carousel carousel-dark slide" data-bs-ride="carousel">
  <ol id="carousel-indicators-${rowNum}" class="carousel-indicators">
    <li data-bs-target="carousel-${rowNum}" data-bs-slide-to="0" class="active"></li>
  </ol>
  <div id="image-preview-${rowNum}" class="carousel-inner mb-3">
    <div class="carousel-item active">
      <img class="d-block w-100" src="/resources/commonImages/no-image.jpg" alt="First slide" />
    </div>
  </div>
  <a class="carousel-control-prev" href="carousel-${rowNum}" role="button" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </a>
  <a class="carousel-control-next" href="carousel-${rowNum}" role="button" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </a>
</div>
<!-- / Carousel -->
<!-- Image List -->
<div id="image-list-${rowNum}"></div>
<!-- / Image List -->
