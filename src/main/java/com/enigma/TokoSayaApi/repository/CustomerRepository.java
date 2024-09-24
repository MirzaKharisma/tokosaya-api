package com.enigma.TokoSayaApi.repository;

import com.enigma.TokoSayaApi.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByUserId(String userId);
    List<Customer> findAllByTransactionsTransactionDetailsProductId(String productId);
}
