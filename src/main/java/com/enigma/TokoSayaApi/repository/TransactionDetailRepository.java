package com.enigma.TokoSayaApi.repository;

import com.enigma.TokoSayaApi.model.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
}
