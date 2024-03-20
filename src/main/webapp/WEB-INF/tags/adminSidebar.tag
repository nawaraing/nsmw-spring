<%@ tag language="java" pageEncoding="UTF-8"%>
<aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
  <div class="app-brand demo">
    <img src="commonImages/favicon.png" height="35" />
    <a href="dashboard" class="app-brand-link">
      <span class="app-brand-text demo menu-text fw-bolder ms-2">NAEDDOCO</span>
    </a>

    <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto d-block d-xl-none">
      <i class="bx bx-chevron-left bx-sm align-middle"></i>
    </a>
  </div>

  <div class="menu-inner-shadow"></div>

  <ul class="menu-inner py-1">
    <li class="menu-item active">
      <a href="dashboard" class="menu-link">
        <i class="menu-icon tf-icons bx bx-home-circle"></i>
        <div>대시보드</div>
      </a>
    </li>
    <li class="menu-item">
      <a href="productList" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>상품 목록</div>
      </a>
    </li>
    <li class="menu-item">
      <a href="memberList" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>회원 목록</div>
      </a>
    </li>

    <li class="menu-header small">
      <span class="menu-header-text">판매 통계</span>
    </li>
    <li class="menu-item">
      <a href="statDate" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>기간별</div>
      </a>
    </li>
    <li class="menu-item">
      <a href="statProduct" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>상품별</div>
      </a>
    </li>
    
    <li class="menu-header small">
      <span class="menu-header-text">쿠폰 지급</span>
    </li>
    <li class="menu-item">
      <a href="couponGrade" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>등급별 자동 발송</div>
      </a>
    </li>
    <li class="menu-item">
      <a href="couponBatch" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>전체 발송</div>
      </a>
    </li>
    <li class="menu-item">
      <a href="couponDownload" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>사용자 다운로드</div>
      </a>
    </li>
    
    <li class="menu-header small">
      <span class="menu-header-text">기타</span>
    </li>
    <li class="menu-item">
      <a href="/" class="menu-link">
        <i class="menu-icon tf-icons bx bx-layout"></i>
        <div>내 쇼핑몰</div>
      </a>
    </li>
    
  </ul>
</aside>
