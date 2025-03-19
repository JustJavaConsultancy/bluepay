package com.techcrunch.bluepay.service;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<Service, Long> {

    boolean existsByCodeIgnoreCase(String code);

}
