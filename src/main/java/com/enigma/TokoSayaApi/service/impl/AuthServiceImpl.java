package com.enigma.TokoSayaApi.service.impl;

import com.enigma.TokoSayaApi.constant.ERole;
import com.enigma.TokoSayaApi.model.authorize.UserAccount;
import com.enigma.TokoSayaApi.model.dto.request.CustomerRequest;
import com.enigma.TokoSayaApi.model.dto.request.LoginRequest;
import com.enigma.TokoSayaApi.model.dto.request.MerchantRequest;
import com.enigma.TokoSayaApi.model.dto.request.RegisterUserRequest;
import com.enigma.TokoSayaApi.model.dto.response.CustomerResponse;
import com.enigma.TokoSayaApi.model.dto.response.LoginUserResponse;
import com.enigma.TokoSayaApi.model.dto.response.MerchantResponse;
import com.enigma.TokoSayaApi.model.dto.response.RegisterUserResponse;
import com.enigma.TokoSayaApi.model.entity.Role;
import com.enigma.TokoSayaApi.model.entity.User;
import com.enigma.TokoSayaApi.repository.UserRepository;
import com.enigma.TokoSayaApi.service.*;
import com.enigma.TokoSayaApi.utils.exceptions.EmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final MerchantService merchantService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterUserResponse<CustomerResponse> registerCustomer(RegisterUserRequest<CustomerRequest> registerUserRequest) {
        if(userRepository.existsByEmail(registerUserRequest.getEmail())) {
            throw new EmailAlreadyExistException("Email already exist");
        }
        Role role = roleService.getOrSave(ERole.CUSTOMER);
        User user = User.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .role(role)
                .createdAt(System.currentTimeMillis())
                .build();
        user = userRepository.saveAndFlush(user);
        CustomerRequest customerRequest = registerUserRequest.getData();
        customerRequest.setUser(user);
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return RegisterUserResponse.<CustomerResponse>builder()
                .id(user.getId())
                .email(user.getEmail())
                .roleName(user.getRole().getName().name())
                .data(customerResponse)
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterUserResponse<MerchantResponse> registerMerchant(RegisterUserRequest<MerchantRequest> registerUserRequest) {
        if(userRepository.existsByEmail(registerUserRequest.getEmail())) {
            throw new EmailAlreadyExistException("Email already exist");
        }
        Role role = roleService.getOrSave(ERole.MERCHANT);
        User user = User.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .role(role)
                .createdAt(System.currentTimeMillis())
                .build();
        user = userRepository.saveAndFlush(user);
        MerchantRequest merchantRequest = registerUserRequest.getData();
        merchantRequest.setUser(user);
        MerchantResponse merchantResponse = merchantService.createMerchant(merchantRequest);
        return RegisterUserResponse.<MerchantResponse>builder()
                .id(user.getId())
                .email(user.getEmail())
                .roleName(user.getRole().getName().name())
                .data(merchantResponse)
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public LoginUserResponse login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        Authentication authenticated = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        UserAccount userAccount = (UserAccount) authenticated.getPrincipal();

        String token = jwtService.generateToken(userAccount);
        return LoginUserResponse.builder()
                .userId(userAccount.getId())
                .token(token)
                .email(userAccount.getEmail())
                .role(userAccount.getRole().getName())
                .build();
    }

    @Override
    public boolean validateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getPrincipal().toString()).orElse(null);
        return user != null;
    }
}
