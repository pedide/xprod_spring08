package com.xprod.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xprod.rest.entity.Produits;

public interface IProduitsRepository extends JpaRepository<Produits,Long>{

}
