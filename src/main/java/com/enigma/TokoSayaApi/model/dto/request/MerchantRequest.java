package com.enigma.TokoSayaApi.model.dto.request;

import com.enigma.TokoSayaApi.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @JsonProperty("phone_number")
    @NotBlank
    private String phoneNumber;

    @JsonIgnore
    private User user;
}
