package com.enigma.TokoSayaApi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private String id;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("transaction_details")
    private List<TransactionDetailResponse> transactionDetailsResponses;

    @JsonProperty("total_payment")
    private Long totalPayment;

    @JsonProperty("created_at")
    private Long createdAt;
}
