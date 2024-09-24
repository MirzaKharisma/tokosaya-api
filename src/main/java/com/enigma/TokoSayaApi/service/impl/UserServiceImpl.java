package com.enigma.TokoSayaApi.service.impl;

import com.enigma.TokoSayaApi.model.authorize.UserAccount;
import com.enigma.TokoSayaApi.model.entity.User;
import com.enigma.TokoSayaApi.repository.UserRepository;
import com.enigma.TokoSayaApi.service.UserAccountService;
import com.enigma.TokoSayaApi.service.UserService;
import com.enigma.TokoSayaApi.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAccountService userAccountService;

    @Override
    @Transactional(readOnly = true)
    public User getUserByTokenForTsx() {
        UserAccount userAccount = userAccountService.getByContext();
        return findByIdOrThrow(userAccount.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByIdForTsx(String id) {
        return findByIdOrThrow(id);
    }

    private User findByIdOrThrow(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
