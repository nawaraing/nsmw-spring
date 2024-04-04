<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>주소 팝업</title>
</head>
<body onload="init();">
    <form id="form" name="form" method="post">
        <input type="hidden" id="confmKey" name="confmKey" value="" /> 
        <input type="hidden" id="returnUrl" name="returnUrl" value="" /> 
        <input type="hidden" id="resultType" name="resultType" value="" />
    </form>
    
    <script language="javascript">
        function init() {
            var url = location.href;
            var confmKey = "devU01TX0FVVEgyMDI0MDExNDE2MDU1MzExNDQyOTQ=";
            var resultType = "4"; // 도로명주소 검색결과 화면 출력내용
            var inputYn = "${param.inputYn}";
            var index = "${param.index}"; // 인덱스 값을 가져옵니다.
            var subscriptionInfoID = "${param.subscriptionInfoID}"; // 인덱스 값을 가져옵니다.

            if (inputYn != "Y") {
                document.form.confmKey.value = confmKey;
                document.form.returnUrl.value = url;
                document.form.resultType.value = resultType;
                document.form.action = "https://business.juso.go.kr/addrlink/addrLinkUrl.do"; // 인터넷망
                document.form.submit();
            } else {
                
                // 오프너 창에 선택된 주소 정보와 인덱스 값을 전달합니다.
                opener.jusoCallBack("${param.zipNo}", "${param.roadAddrPart1}", "${param.addrDetail}", subscriptionInfoID, index);
                // 팝업 창을 닫습니다.
                window.close();
            }
        }
    </script>
</body>
</html>
