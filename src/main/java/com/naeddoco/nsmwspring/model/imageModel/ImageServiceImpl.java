package com.naeddoco.nsmwspring.model.imageModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageDAO ImageDAO;
	
	@Override
	public List<ImageDTO> selectAll(ImageDTO imageDTO) {
		
		return ImageDAO.selectAll(imageDTO);
		
	}

	@Override
	public ImageDTO selectOne(ImageDTO imageDTO) {
		
		return ImageDAO.selectOne(imageDTO);
		
	}

	@Override
	public boolean insert(ImageDTO imageDTO) {
		
		return ImageDAO.insert(imageDTO);
		
	}

	@Override
	public boolean update(ImageDTO imageDTO) {
		
		return ImageDAO.update(imageDTO);
		
	}

	@Override
	public boolean delete(ImageDTO imageDTO) {
		
		return ImageDAO.delete(imageDTO);
		
	}

}