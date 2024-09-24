package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.entity.User;

public interface UserService {
    User getUserByTokenForTsx();
    User getUserByIdForTsx(String id);
}
