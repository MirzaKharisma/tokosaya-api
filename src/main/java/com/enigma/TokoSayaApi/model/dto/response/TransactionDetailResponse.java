package com.enigma.TokoSayaApi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetailResponse {
    private String id;
    private String productId;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private Long quantity;
}
