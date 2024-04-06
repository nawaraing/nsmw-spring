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
      <form method="POST" enctype="multipart/form-data" action="/productList/imageUpdate">
        <div class="modal-body">
          <custom:adminImageHandle rowNum="${rowNum}"/>
        </div>
        <div class="text-center mb-3">
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
      </form>
    </div>
  </div>
</div>
