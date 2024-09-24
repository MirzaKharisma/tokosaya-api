package com.enigma.TokoSayaApi.repository;

import com.enigma.TokoSayaApi.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
