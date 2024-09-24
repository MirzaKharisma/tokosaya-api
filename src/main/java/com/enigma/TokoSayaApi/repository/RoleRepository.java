package com.enigma.TokoSayaApi.repository;

import com.enigma.TokoSayaApi.constant.ERole;
import com.enigma.TokoSayaApi.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
