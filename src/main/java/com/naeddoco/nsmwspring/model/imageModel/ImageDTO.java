package com.naeddoco.nsmwspring.model.imageModel;

import lombok.Data;

@Data
public class ImageDTO {
	private int imageID; // 이미지아이디
	private String imagePath; // 이미지 경로
	
    private String searchCondition; // 쿼리 분기

}
