package com.enigma.TokoSayaApi.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetailRequest {
    private String transactionId;
    private String productId;
    private Long quantity;
}
