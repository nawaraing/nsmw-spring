/*
* Image Component
*/

function adminImageHandle(rowNum, imagePath) {
	let div, ol, li, imagePreview, imagePreviewImage, imageListImage, img, a, ret, imageName, deleteButton;
	console.log('imagePath: ' + imagePath);
	let imagePathList = imagePath.split(';');
	
	div = $('<div id="carousel-' + rowNum + '" class="carousel carousel-dark slide" data-bs-ride="carousel">');
	ol = $('<ol id="carousel-indicators-' + rowNum + '" class="carousel-indicators">');
	
	imagePathList.forEach(function (image, index) {
		let active = '';
		if (index === 0) {
			active = 'class="active"';
		}
		
		li = $('<li id="carousel-indicator-' + rowNum + '-image-' + index + '" data-bs-target="carousel-' + rowNum + '" data-bs-slide-to="' + index + '" ' + active + '>');
		ol.append(li);
	});
	div.append(ol);
	
	imagePreview = $('<div id="image-preview-' + rowNum + '" class="carousel-inner mb-3">');
	imagePathList.forEach(function (image, index) {
		let active = '';
		if (index === 0) {
			active = 'active';
		}
		
		imagePreviewImage = $('<div id="image-preview-' + rowNum + '-image-' + index + '" class="carousel-item ' + active + '">');
		img = $('<img class="d-block w-100" src="' + image + '" alt="First slide" />');
		imagePreviewImage.append(img);
		imagePreview.append(imagePreviewImage);
	});
	div.append(imagePreview);
	
	a = $('<a class="carousel-control-prev" href="#carousel-' + rowNum + '" role="button" data-bs-slide="prev">');
	a.append($('<span class="carousel-control-prev-icon" aria-hidden="true">'));
	a.append($('<span class="visually-hidden">').text('Previous'));
	div.append(a);
	a = $('<a class="carousel-control-next" href="#carousel-' + rowNum + '" role="button" data-bs-slide="next">');
	a.append($('<span class="carousel-control-next-icon" aria-hidden="true">'));
	a.append($('<span class="visually-hidden">').text('Next'));
	div.append(a);
	
	ret = $('<div>');
	ret.append(div);
	
	div = $('<div id="image-list-' + rowNum + '">');
	imagePathList.forEach(function (image, index) {
		if (image === '/resources/commonImages/no-image.jpg') {
			return true;
		}
		
		imageListImage = $('<div id="image-list-' + rowNum + '-image-' + index + '" class="d-flex mb-3">');
		imageListImage.append($('<div class="flex-grow-1 row">'));
		
		imageName = $('<div class="col-10 col-sm-10 mb-sm-0 mb-2">');
		let imageList = image.split('/');
		h4 = $('<h4 class="mb-0 text-truncate">').text(imageList[imageList.length - 1]);
		imageName.append(h4);
		imageListImage.append(imageName);
		
		imageListImage.append($('<input name="imagePaths" value="' + image + '" type="hidden" />'));
		
		deleteButton = $('<div class="col-2 col-sm-2 text-end">');
		deleteButton.append($('<button type="button" class="btn btn-icon btn-outline-danger" onclick="deleteImage(\'' + rowNum + '\', ' + index + ', 1)">').append($('<i class="bx bx-trash-alt">')));
		imageListImage.append(deleteButton);
		
		div.append(imageListImage);
	});
	ret.append(div);
	
	return ret;
}
function adminImageUploadButton(rowNum, maxImageNum) {
	let ret = $('<div>');
	
	ret.append($('<input id="upload-image-' + rowNum + '" type="file" name="images" multiple class="btn btn-primary me-2" onchange="displayImage(\'' + rowNum + '\', ' + maxImageNum + ')" style="display: none;" required />'));
	ret.append($('<div id="too-many-images-' + rowNum + '">'));
	ret.append($('<button id="upload-image-button-' + rowNum + '" type="button" class="btn btn-primary me-2" onclick="$(\'#upload-image-' + rowNum + '\').click();">').text('이미지 업로드'));
	
	return ret;
}