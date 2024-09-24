package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.dto.request.TransactionRequest;
import com.enigma.TokoSayaApi.model.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
}
