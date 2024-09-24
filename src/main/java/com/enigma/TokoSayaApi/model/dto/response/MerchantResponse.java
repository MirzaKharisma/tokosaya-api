package com.enigma.TokoSayaApi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantResponse {
    private String id;
    private String name;
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("created_at")
    private Long createdAt;
}
