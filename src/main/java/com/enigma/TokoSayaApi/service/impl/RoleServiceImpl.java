package com.enigma.TokoSayaApi.service.impl;

import com.enigma.TokoSayaApi.constant.ERole;
import com.enigma.TokoSayaApi.model.entity.Role;
import com.enigma.TokoSayaApi.repository.RoleRepository;
import com.enigma.TokoSayaApi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> roleOptional = roleRepository.findByName(role);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        }
        Role currentRole = Role.builder()
                .name(role)
                .build();
        return roleRepository.saveAndFlush(currentRole);
    }
}
