package com.xprod.rest.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xprod.rest.entity.Produits;
import com.xprod.rest.repository.IProduitsRepository;

@Service
public class ProduitsDao {

	@Autowired
	IProduitsRepository produitsRepository;
	
	//Liste  des produits
	public List<Produits> getProduits(){
				
		return produitsRepository.findAll();
		
	}
	
	//Save
	public Produits saveProduit(Produits produit) {
		return produitsRepository.save(produit);
	}
	
	//get a produit
	public Produits getProduitById(Long idProd) {
		return produitsRepository.findById(idProd).get();
	}
	
	//Delete a produit
	public void deleteproduit(Produits produit) {
		produitsRepository.delete(produit); 
	}
	
}
