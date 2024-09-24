package com.enigma.TokoSayaApi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {
    @JsonProperty("status_code")
    private Integer statusCode;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
