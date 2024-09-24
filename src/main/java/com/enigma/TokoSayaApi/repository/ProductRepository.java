package com.enigma.TokoSayaApi.repository;

import com.enigma.TokoSayaApi.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
