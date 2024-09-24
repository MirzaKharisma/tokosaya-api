package com.enigma.TokoSayaApi.controller;

import com.enigma.TokoSayaApi.constant.APIUrl;
import com.enigma.TokoSayaApi.model.dto.request.CustomerRequest;
import com.enigma.TokoSayaApi.model.dto.request.LoginRequest;
import com.enigma.TokoSayaApi.model.dto.request.MerchantRequest;
import com.enigma.TokoSayaApi.model.dto.request.RegisterUserRequest;
import com.enigma.TokoSayaApi.model.dto.response.*;
import com.enigma.TokoSayaApi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<CommonResponse<RegisterUserResponse<CustomerResponse>>> registerCustomer(
            @Valid @RequestBody RegisterUserRequest<CustomerRequest> registerUserRequest
    )
    {
        RegisterUserResponse<CustomerResponse> registerUserResponse = authService.registerCustomer(registerUserRequest);

        CommonResponse<RegisterUserResponse<CustomerResponse>> commonResponse = CommonResponse.<RegisterUserResponse<CustomerResponse>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Customer registered successfully")
                .data(registerUserResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/register/merchant")
    public ResponseEntity<CommonResponse<RegisterUserResponse<MerchantResponse>>> registerMerchant(
            @Valid @RequestBody RegisterUserRequest<MerchantRequest> registerUserRequest
    )
    {
        RegisterUserResponse<MerchantResponse> registerUserResponse = authService.registerMerchant(registerUserRequest);

        CommonResponse<RegisterUserResponse<MerchantResponse>> commonResponse = CommonResponse.<RegisterUserResponse<MerchantResponse>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Merchant registered successfully")
                .data(registerUserResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginUserResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginUserResponse loginUserResponse = authService.login(loginRequest);
        CommonResponse<LoginUserResponse> commonResponse = CommonResponse.<LoginUserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .data(loginUserResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
