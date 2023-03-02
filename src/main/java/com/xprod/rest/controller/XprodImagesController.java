package com.xprod.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xprod.rest.exception.domain.XprodImages;
import com.xprod.rest.service.impl.XprodImagesImpl;

@RestController
@RequestMapping
@CrossOrigin("*")
public class XprodImagesController {


	@Autowired
	XprodImagesImpl xprodImagesImpl;
	

	@PostMapping("/image")
	public XprodImages createImage(@Validated @RequestBody(required = false) XprodImages img) {
		return xprodImagesImpl.createImage(img);

	}
	
	@GetMapping("/image/{id}")
	public ResponseEntity findImageById(@PathVariable(name = "id") Long id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("Can't find Image with NULL ID from ImageController ");
		}
		// je rentre dans ma base de données en passant par images dao
		XprodImages img = xprodImagesImpl.getImageById(id);

		if (img == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(img);
	}
	
	@GetMapping("/image")
	public List<XprodImages> getAllImages(@Validated @RequestBody(required = false) XprodImages img) {
		return xprodImagesImpl.getAllImages();

	}
	
	@PutMapping("/image/{id}")
	public ResponseEntity<XprodImages> updateImage(@Validated @PathVariable(name = "id") Long id,
			@RequestBody(required = false) XprodImages img) {

		if (img == null) {
			return ResponseEntity.notFound().build();
		}
		img.setId(id);
		xprodImagesImpl.updateImage(img);

		return ResponseEntity.ok().body(img);
	}
	
	@DeleteMapping("/image/{id}")
	public ResponseEntity<XprodImages> deleteImage(@Validated @PathVariable(name = "id") Long id) {
		// Je mets mon image dans une variable et je le recupere en fonction de son ID
		XprodImages img = xprodImagesImpl.getImageById(id);
		// Une fois que j ai l ID du image en passant par imageImpl si le image est
		// null

		if (img == null) {
			return ResponseEntity.notFound().build();

		}
		
		xprodImagesImpl.deleteImage(img);

		return ResponseEntity.ok().body(img);
	}

}
