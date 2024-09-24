package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.dto.request.CustomerRequest;
import com.enigma.TokoSayaApi.model.dto.request.LoginRequest;
import com.enigma.TokoSayaApi.model.dto.request.MerchantRequest;
import com.enigma.TokoSayaApi.model.dto.request.RegisterUserRequest;
import com.enigma.TokoSayaApi.model.dto.response.CustomerResponse;
import com.enigma.TokoSayaApi.model.dto.response.LoginUserResponse;
import com.enigma.TokoSayaApi.model.dto.response.MerchantResponse;
import com.enigma.TokoSayaApi.model.dto.response.RegisterUserResponse;

public interface AuthService {
    RegisterUserResponse<CustomerResponse> registerCustomer(RegisterUserRequest<CustomerRequest> registerUserRequest);
    RegisterUserResponse<MerchantResponse> registerMerchant(RegisterUserRequest<MerchantRequest> registerUserRequest);
    LoginUserResponse login(LoginRequest loginRequest);
    boolean validateToken();
}
