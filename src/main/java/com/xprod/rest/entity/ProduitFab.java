package com.xprod.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PRODUITFAB")
public class ProduitFab extends Produits{

	@Column(name="NBHEUREMOY") 
	private int nbHeureMoy;
	

	public ProduitFab(Long id, String reference, String designation, String descriptif, double prixVente) {
		super(id, reference, designation, descriptif, prixVente);
		
	}

	public ProduitFab(String reference, String designation, String descriptif, double prixVente) {
		super(reference, designation, descriptif, prixVente);
		
	}

	public ProduitFab() {
		super();
	}

	public int getNbHeureMoy() {
		return nbHeureMoy;
	}

	public void setNbHeureMoy(int nbHeureMoy) {
		this.nbHeureMoy = nbHeureMoy;
	}
	
	
	
}
