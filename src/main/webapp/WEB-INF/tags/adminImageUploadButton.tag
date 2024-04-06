<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<%@ attribute name="rowNum" %>

<input id="upload-image-${rowNum}" type="file" name="images" multiple class="btn btn-primary me-2" onchange="displayImage('${rowNum}', 3)" style="display: none;" required />
<div id="too-many-images-${rowNum}"></div>
<button type="button" class="btn btn-primary me-2" onclick="$('#upload-image-${rowNum}').click();">이미지 업로드</button>
