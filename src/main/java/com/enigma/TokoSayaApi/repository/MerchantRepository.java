package com.enigma.TokoSayaApi.repository;

import com.enigma.TokoSayaApi.model.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Optional<Merchant> findByUserId(String userId);
}
