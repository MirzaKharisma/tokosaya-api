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
public class RegisterUserResponse<T> {
    private String id;
    private String email;

    @JsonProperty("role_name")
    private String roleName;

    private T data;

    @JsonProperty("created_at")
    private Long createdAt;
}
