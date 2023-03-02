package com.xprod.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.xprod.rest.exception.domain.XprodImages;

public interface IXprodImagesRepository extends JpaRepository<XprodImages,Long>{

}
