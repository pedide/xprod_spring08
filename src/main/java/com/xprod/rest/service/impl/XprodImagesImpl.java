package com.xprod.rest.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xprod.rest.exception.domain.XprodImages;
import com.xprod.rest.repository.IXprodImagesRepository;




@Service
public class XprodImagesImpl {

	@Autowired
	private IXprodImagesRepository iXprodImagesRepository;
	

	// Create Image (Create)
	public XprodImages createImage(XprodImages img) {
		return iXprodImagesRepository.save(img);
	}

	// Read Image by Id
	public XprodImages getImageById(Long id) {
		return iXprodImagesRepository.findById(id).get();
	}
	
	//List Images
	public List<XprodImages> getAllImages() {
		return iXprodImagesRepository.findAll();
	}
	
	// Update Image
	public XprodImages updateImage(@Valid XprodImages img) {	
		return iXprodImagesRepository.saveAndFlush(img);		
	}
	
	// Delete Images
	public void deleteImage(XprodImages img) {
		iXprodImagesRepository.delete(img);
	}
}
