package com.xprod.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="PRODUIT")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Produits {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDPRODUIT")
	private Long id;
	
	@Column(name="REF")
	private String reference;
	
	@Column(name="DESIGNATION")
	private String designation;
	
	@Column(name="DESCRIPTIF")
	private String descriptif;
	
	@Column(name="PRIXVENTE")
	private double prixVente;
	
	@Column(name="produitImage")
	private String img;
	
	@Column(name="produitStockQuantity")
	private int stockQty;
	
	@Column(name="produitIsActive")
	private Boolean active;
	
	
	public Produits() {
		super();
	}
	
	

	public Produits(String reference, String designation, String descriptif, double prixVente) {
		super();
		this.reference = reference;
		this.designation = designation;
		this.descriptif = descriptif;
		this.prixVente = prixVente;
	}



	public Produits(Long id, String reference, String designation, String descriptif, double prixVente, String img,
			int stockQty, Boolean active) {
		super();
		this.id = id;
		this.reference = reference;
		this.designation = designation;
		this.descriptif = descriptif;
		this.prixVente = prixVente;
		this.img = img;
		this.stockQty = stockQty;
		this.active = active;
	}



	public Produits(Long id, String reference, String designation, String descriptif, double prixVente) {
		super();
		this.id = id;
		this.reference = reference;
		this.designation = designation;
		this.descriptif = descriptif;
		this.prixVente = prixVente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescriptif() {
		return descriptif;
	}

	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}



	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}



	public int getStockQty() {
		return stockQty;
	}



	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	@Override
	public String toString() {
		return "Produits [id=" + id + ", reference=" + reference + ", designation=" + designation + ", descriptif="
				+ descriptif + ", prixVente=" + prixVente + ", img=" + img + ", stockQty=" + stockQty + ", active="
				+ active + "]";
	}



	
	 
	
	 
}
