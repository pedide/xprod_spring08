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

import com.xprod.rest.entity.ProduitFab;
import com.xprod.rest.service.impl.ProduitFabServiceImpl;

@RestController
@RequestMapping
@CrossOrigin("*")
public class ProduitFabController {
	@Autowired
	ProduitFabServiceImpl produitFabServiceImpl;
	
	@PostMapping("/produit-fab")
	public ProduitFab createProduitFab(@Validated 
			@RequestBody(required=false)ProduitFab produitFab) {
		return produitFabServiceImpl.saveProdruitFab(produitFab);
	}
	
	@GetMapping("/produit-fab")
	public List<ProduitFab> getAllProduitFab(@Validated 
			@RequestBody(required=false)ProduitFab produitFab) {
		return produitFabServiceImpl.getAllProduitFab();
	}
	
	@PutMapping("/produit-fab")
	public ProduitFab updateProduitFab(@Validated 
			@RequestBody(required=false)ProduitFab produitFab) {
		return produitFabServiceImpl.saveProdruitFab(produitFab);
	}
	
	@GetMapping("/produit-fab/{idProduitFab}")
	public ResponseEntity findProduitFabById(@PathVariable(name="idProduitFab")Long idProduitFab) {
		if(idProduitFab== null) {
			return ResponseEntity.badRequest().body("Cannot retrieve ProduitFab with null Id !");
		}
		ProduitFab produitFab = produitFabServiceImpl.getProduitFabById(idProduitFab);
		if(produitFab == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(produitFab);
	}
	

	@DeleteMapping("/produit-fab/{id}")
	public ResponseEntity deleteProduitFab(@PathVariable(name="id")Long idProduitFab) {
		
		ProduitFab produitFab = produitFabServiceImpl.getProduitFabById(idProduitFab);
		if(produitFab == null) {
			return ResponseEntity.notFound().build();
		}
		produitFabServiceImpl.deleteProduitFab(produitFab);
		return ResponseEntity.ok().body(produitFab);
	}

}
