<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%@ attribute name="colName" %>
<%@ attribute name="colIndex" %>
<%@ attribute name="categoryList" %>

<script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>

<a
  class="btn btn-outline-primary dropdown-toggle"
  href="javascript:void(0)"
  id="categories-${colName}-${colIndex}"
  role="button"
  data-bs-toggle="dropdown"
  aria-expanded="false"
>
  <c:forEach var="category" items="${categoryList}" varStatus="categoryStatus">
    <span class="badge bg-label-info me-1" id="${colName}-${colIndex}-category-${categoryStatus.index}">${category}</span>
  </c:forEach>
</a>
<ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="checkbox-categories-${status.index}">
  <c:forEach var="category" items="${categoryList}" varStatus="categoryStatus">
    <li>
      <div class="form-check">
        <label class="dropdown-item" for="checkbox-${colName}-${colIndex}-category-${categoryStatus.index}" id="dropdown-item-coupon-${status.index}-category-${categoryStatus.index}">
          <input class="form-check-input" type="checkbox" value="${category.categoryName}" id="checkbox-${colName}-${colIndex}-category-${categoryStatus.index}" />${category.categoryName}
        </label>
      </div>
    </li>
  </c:forEach>
</ul>
