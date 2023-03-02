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

import com.xprod.rest.dao.ProduitsDao;
import com.xprod.rest.entity.Produits;

@RestController
@RequestMapping
@CrossOrigin("*")
public class ProduitsController {

	@Autowired
	ProduitsDao produitsDao;
	
	@GetMapping("/produits")
	public  List<Produits> getAllProduits(@Validated @RequestBody(required=false) Produits produits){
		return produitsDao.getProduits();
		
	}
	
	@PostMapping("/produits")
	public Produits createProduit(@Validated @RequestBody(required=false) Produits produits) {
		return produitsDao.saveProduit(produits);
	}
	@GetMapping("/produits/{idProduit}")
	public ResponseEntity findProduitById(@PathVariable(name="idProduit") Long idProduit) {
		if(idProduit == null) {
			return ResponseEntity.badRequest().body("Cannot retrieve produit with null ID");
		}
		Produits produit = produitsDao.getProduitById(idProduit);
		
		if(produit==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(produit);
		
	}
	@PutMapping("/produits")
	public Produits updateProduit(@Validated @RequestBody(required=false) Produits produits) {
		return produitsDao.saveProduit(produits);
	}
	
	@DeleteMapping("/produits/{id}")
	public ResponseEntity<Produits> deleteProduits(@Validated @PathVariable(name="id") Long idProduit){
		Produits produit = produitsDao.getProduitById(idProduit);
		if(produit==null) {
			return ResponseEntity.notFound().build();
		}
		produitsDao.deleteproduit(produit);
		
		return ResponseEntity.ok().body(produit);
	}
	
	
	
	
	
	
}
