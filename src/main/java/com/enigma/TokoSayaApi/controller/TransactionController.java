package com.enigma.TokoSayaApi.controller;

import com.enigma.TokoSayaApi.constant.APIUrl;
import com.enigma.TokoSayaApi.model.dto.request.TransactionRequest;
import com.enigma.TokoSayaApi.model.dto.response.CommonResponse;
import com.enigma.TokoSayaApi.model.dto.response.TransactionResponse;
import com.enigma.TokoSayaApi.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIUrl.TRANSACTION_API)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);

        CommonResponse<TransactionResponse> commonResponse = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Transaction Created")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
}
