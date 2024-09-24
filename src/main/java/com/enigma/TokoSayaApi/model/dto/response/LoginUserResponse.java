package com.enigma.TokoSayaApi.model.dto.response;

import com.enigma.TokoSayaApi.constant.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserResponse {
    private String userId;
    private String email;
    private String token;
    private ERole role;
}
