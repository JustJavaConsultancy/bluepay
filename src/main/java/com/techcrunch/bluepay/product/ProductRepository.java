package com.techcrunch.bluepay.product;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCodeIgnoreCase(String code);

}
