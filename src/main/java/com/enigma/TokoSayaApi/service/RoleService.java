package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.constant.ERole;
import com.enigma.TokoSayaApi.model.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
