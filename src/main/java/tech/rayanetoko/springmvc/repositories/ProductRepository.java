package tech.rayanetoko.springmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.rayanetoko.springmvc.entities.Product;

/**
 * ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

	
}
