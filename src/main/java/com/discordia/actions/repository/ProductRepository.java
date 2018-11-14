package com.discordia.actions.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discordia.actions.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByActions_IdAndNameContainingIgnoreCaseAndQuantityGreaterThan(Long actionId, String searchText, Long quantity);
  List<Product> findByActions_IdAndQuantityGreaterThan(Long actionId, Long quantity);
  Optional<Product> findByIdAndActions_Id(Long productId, Long actionId);
}
