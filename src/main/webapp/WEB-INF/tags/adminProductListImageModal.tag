<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<%@ attribute name="rowNum" %>

<script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>
<script src="/resources/admin/js/checkbox.js"></script>

<!-- Button trigger modal -->
<button
  type="button"
  class="btn btn-primary me-2"
  data-bs-toggle="modal"
  data-bs-target="#imageModal-${rowNum}"
>이미지 확인</button>

<!-- Modal -->
<div class="modal fade" id="imageModal-${rowNum}" data-bs-backdrop="static" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle">이미지 조회/변경/삭제</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"
        ></button>
      </div>
      <form method="POST" enctype="multipart/form-data" action="/productList/insert">
        <div class="modal-body">
              <!-- Carousel -->
              <div id="carouselExample" class="carousel carousel-dark slide" data-bs-ride="carousel">
                <ol class="carousel-indicators" id="carousel-indicators-${rowNum}">
                  <li data-bs-target="#carouselExample" data-bs-slide-to="0" class="active"></li>
                </ol>
                <div class="carousel-inner mb-3" id="image-preview-${rowNum}">
                  <div class="carousel-item active">
                    <img class="d-block w-100" src="/resources/commonImages/no-image.jpg" alt="First slide" />
                  </div>
                </div>
              </div>
              <a class="carousel-control-prev" href="#carouselExample" role="button" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </a>
              <a class="carousel-control-next" href="#carouselExample" role="button" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </a>
            <!-- / Carousel -->
            <!-- Image List -->
            <div id="image-list-${rowNum}"></div>
            <!-- / Image List -->
          </div>
          <div class="text-center mb-3">
            <input type="file" name="images" multiple class="btn btn-primary me-2" id="upload-image-${rowNum}" onchange="displayImage(${rowNum}, 3)" style="display: none;"/>
            <div id="too-many-images-${rowNum}"></div>
            <button type="button" class="btn btn-primary me-2" onclick="$('#upload-image-${rowNum}').click();">이미지 업로드</button>
            <button
              type="button"
              class="btn btn-secondary me-2"
              data-bs-dismiss="modal"
            >취소</button>
            <button
              type="submit"
              id="submit-button"
              class="btn btn-success me-2"
            >저장</button>
          </div>
      </form>
    </div>
  </div>
</div>
