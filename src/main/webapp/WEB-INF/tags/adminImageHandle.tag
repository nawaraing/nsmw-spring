<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="rowNum" %>
<%@ attribute name="imagePath" %>

<c:set var="imagePathList" value="${fn:split(imagePath, ';')}" />

<!-- Carousel -->
<div id="carousel-${rowNum}" class="carousel carousel-dark slide" data-bs-ride="carousel">
  <ol id="carousel-indicators-${rowNum}" class="carousel-indicators">
    <c:forEach var="image" items="${imagePathList}" varStatus="status">
      <c:if test="${status.index eq 0}">
        <li id="carousel-indicator-${rowNum}-image-${status.index}" data-bs-target="carousel-${rowNum}" data-bs-slide-to="${status.index}" class="active"></li>
      </c:if>
      <c:if test="${status.index ne 0}">
        <li id="carousel-indicator-${rowNum}-image-${status.index}" data-bs-target="carousel-${rowNum}" data-bs-slide-to="${status.index}"></li>
      </c:if>
    </c:forEach>
  </ol>
  <div id="image-preview-${rowNum}" class="carousel-inner mb-3">
    <c:forEach var="image" items="${imagePathList}" varStatus="status">
      <c:if test="${status.index eq 0}">
        <div id="image-preview-${rowNum}-image-${status.index}" class="carousel-item active">
          <img class="d-block w-100" src="${image}" alt="First slide" />
        </div>
      </c:if>
      <c:if test="${status.index ne 0}">
        <div id="image-preview-${rowNum}-image-${status.index}" class="carousel-item">
          <img class="d-block w-100" src="${image}" alt="First slide" />
        </div>
      </c:if>
    </c:forEach>
  </div>
  <a class="carousel-control-prev" href="#carousel-${rowNum}" role="button" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carousel-${rowNum}" role="button" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </a>
</div>
<!-- / Carousel -->
<!-- Image List -->
<div id="image-list-${rowNum}">
  <c:forEach var="image" items="${imagePathList}" varStatus="status">
    <c:if test="${image ne '/resources/commonImages/no-image.jpg'}">
      <div id="image-list-${rowNum}-image-${status.index}" class="d-flex mb-3">
        <div class="flex-grow-1 row"></div>
        <div class="col-10 col-sm-10 mb-sm-0 mb-2">
          <c:set var="imageParse" value="${fn:split(image, '/')}" />
          <h4 class="mb-0 text-truncate">${imageParse[fn:length(imageParse) - 1]}</h4>
        </div>
        <input name="imagePaths" value="${image}" type="hidden" />
        <div class="col-2 col-sm-2 text-end">
          <button type="button" class="btn btn-icon btn-outline-danger" onclick="deleteImage('${rowNum}', ${status.index}, 3)">
            <i class="bx bx-trash-alt"></i>
          </button>
        </div>
      </div>
    </c:if>
  </c:forEach>
</div>
<!-- / Image List -->
