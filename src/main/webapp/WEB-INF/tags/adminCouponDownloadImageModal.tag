<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="rowNum" %>
<%@ attribute name="imagePath" %>

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
        <h5 class="modal-title" id="backDropModalTitle">쿠폰 추가 - 사용자 다운로드 쿠폰</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"
        ></button>
      </div>

      <form method="POST" enctype="multipart/form-data" action="/couponDownload/imageUpdate">
        <div class="modal-body">
          <custom:adminImageHandle rowNum="${rowNum}" imagePath="${imagePath}" />
          <div class="text-center">
            <custom:adminImageUploadButton rowNum="${rowNum}" />
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
        </div>
      </form>
    </div>
  </div>
</div>
