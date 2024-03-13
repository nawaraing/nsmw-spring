<%@ tag language="java" pageEncoding="UTF-8"%>

<style>
	.preview-image {
     	max-width: 200px;
        max-height: 200px;
        margin: 5px;
  	}
</style>

<div class="col-lg-4">
	<input class="btn text-primary" type="file" id="image-input" name="imageFile" accept="image/*"/>
	<div id="image-preview-container"></div>
	<script>
		document.getElementById("image-input").addEventListener("change", function() {
			const files = this.files;
			const previewContainer = document.getElementById("image-preview-container");
			// 기존에 표시된 이미지를 모두 제거
			previewContainer.innerHTML = '';
			// 모든 선택된 파일에 대해 루프를 돌면서 이미지를 표시
			for (let i = 0; i < files.length; i++) {
				const file = files[i];
				const reader = new FileReader();
				reader.onload = function(e) {
					const imgElement = document.createElement("img");
						imgElement.src = e.target.result;
						imgElement.alt = "이미지 미리보기";
						imgElement.className = "preview-image";
						previewContainer.appendChild(imgElement);
					};
					reader.readAsDataURL(file);
				}
			});
	</script>
</div>