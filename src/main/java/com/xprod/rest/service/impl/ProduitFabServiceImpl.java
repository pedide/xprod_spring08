package com.xprod.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xprod.rest.entity.ProduitFab;
import com.xprod.rest.repository.IProduitFabRepository;

@Service
public class ProduitFabServiceImpl {

	@Autowired
	IProduitFabRepository produitFabRepository; 
	
	//get a produitFab
	public ProduitFab getProduitFabById(Long idProdFab) {
		return produitFabRepository.findById(idProdFab).get();
	}
	
	//Lists des produits fabriqués
	public List<ProduitFab> getAllProduitFab(){
		return produitFabRepository.findAll();
	}
	//Save
	public ProduitFab saveProdruitFab(ProduitFab produitFab) {
		return produitFabRepository.save(produitFab);
		
	}
	
	//Delete

	public void deleteProduitFab(ProduitFab produitFab) {
		produitFabRepository.delete(produitFab);
		
	}
	
}
