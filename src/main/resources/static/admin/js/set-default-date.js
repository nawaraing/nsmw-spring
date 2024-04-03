/*
 * 검색 기간 기본 값 설정
 * 일별 통계: (30일 전) ~ (오늘 날짜)
 * 월별 통계: (1년 전) ~ (이번 달)
 */
(function () {
    var today = new Date();
    var todayY = today.getFullYear();
    var todayM = ('0' + (today.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1을 해줍니다.
    var todayD = ('0' + today.getDate()).slice(-2); // 일자를 두 자리로 만들어줍니다.
    var todayMonth = todayY + '-' + todayM;
    var todayDate = todayMonth + '-' + todayD;
    document.getElementById('end-month-input').value = todayMonth;
    document.getElementById('end-date-input').value = todayDate;
  
    var Before30Days = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 30);
    var Before30DaysY = Before30Days.getFullYear();
    var Before30DaysM = ('0' + (Before30Days.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1을 해줍니다.
    var Before30DaysD = ('0' + Before30Days.getDate()).slice(-2); // 일자를 두 자리로 만들어줍니다.
    var startDate = Before30DaysY + '-' + Before30DaysM + '-' + Before30DaysD;
    document.getElementById('start-date-input').value = startDate;

    var Before365Days = new Date(today.getFullYear(), today.getMonth() - 11, today.getDate());
    var Before365DaysY = Before365Days.getFullYear();
    var Before365DaysM = ('0' + (Before365Days.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1을 해줍니다.
    var startMonth = Before365DaysY + '-' + Before365DaysM;
    document.getElementById('start-month-input').value = startMonth;
})();