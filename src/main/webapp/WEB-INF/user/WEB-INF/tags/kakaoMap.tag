<%@ tag language="java" pageEncoding="UTF-8"%>

<div id="map" style="width: 1200px; height: 400px;">
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1fbb9e9595211a29e649370beccf5a78"></script>
</div>

<script type="text/javascript">
	//지도를 담을 영역의 DOM 레퍼런스
	// 지도를 표시할 div
	var mapContainer = document.getElementById('map'),
	//지도를 생성할 때 필요한 기본 옵션
	mapOption = {
		// 지도의 중심좌표
		center : new kakao.maps.LatLng(37.499456, 127.035832),
		// 지도의 확대 레벨
		level : 3
	};

	// 지도를 생성합니다
	var map = new kakao.maps.Map(mapContainer, mapOption);

	// 마커가 표시될 위치입니다 
	var markerPosition = new kakao.maps.LatLng(37.49944608755652,
			127.03584631733999);

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
		position : markerPosition
	});

	// 인포윈도우로 장소에 대한 설명을 표시합니다
	//var infowindow = new kakao.maps.InfoWindow({
	//    content: '<div style="width: auto; text-align: center; padding: 10px;">13층</div>'
	//});
	//infowindow.open(map, marker);

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);

	// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
	// marker.setMap(null);
</script>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services,clusterer,drawing"></script>