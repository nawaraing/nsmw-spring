package com.naeddoco.nsmwspring.model.gradeModel;

import lombok.Data;

@Data
public class GradeDTO {
	private int gradeID; // 등급 아이디
	private String gradeName; // 등급 이름
	private int lowerLimit; // 등급 산정 최소값
	private int upperLimit; // 등급 산정 최대값
	
}
