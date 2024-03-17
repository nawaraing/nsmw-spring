package com.naeddoco.nsmwspring.model.gradeModel;

import java.util.List;

public interface GradeService {
	public List<GradeDTO> selectAll(GradeDTO gradeDTO);
	public GradeDTO selectOne(GradeDTO gradeDTO);
	public boolean insert(GradeDTO gradeDTO);
	public boolean update(GradeDTO gradeDTO);
	public boolean delete(GradeDTO gradeDTO);
}
